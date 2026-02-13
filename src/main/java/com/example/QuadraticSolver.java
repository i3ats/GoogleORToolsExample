package com.example;

import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;
import com.google.ortools.sat.LinearExpr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example demonstrating Google OR-Tools with a quadratic objective function.
 * This example uses CP-SAT solver to minimize a quadratic expression.
 *
 * Problem: Find integer values for x and y that minimize:
 * Objective: (x-5)^2 + (y-3)^2
 *
 * Subject to:
 *   x + y >= 10
 *   x + 2*y <= 14
 *   0 <= x, y <= 10
 *
 * This is equivalent to finding the closest lattice point to (5,3)
 * that satisfies the constraints.
 */
public class QuadraticSolver {
    private static final Logger logger = LoggerFactory.getLogger(QuadraticSolver.class);

    public static void main(String[] args) {

        // Load the OR-Tools native library
        Loader.loadNativeLibraries();

        logger.info("=== Google OR-Tools Quadratic Objective Example ===");
        logger.info("Finding integer point closest to (5,3) subject to constraints");

        // Create the CP-SAT model
        final var model = new CpModel();

        // Create integer variables with bounds [0, 10]
        final var x = model.newIntVar(0, 10, "x");
        final var y = model.newIntVar(0, 10, "y");

        // Add constraint: x + y >= 10
        model.addGreaterOrEqual(LinearExpr.sum(new IntVar[]{x, y}), 10);

        // Add constraint: x + 2*y <= 14
        model.addLessOrEqual(LinearExpr.weightedSum(new IntVar[]{x, y}, new long[]{1, 2}), 14);

        logger.info("Constraints:");
        logger.info("  x + y >= 10");
        logger.info("  x + 2*y <= 14");
        logger.info("  0 <= x, y <= 10");

        // Create auxiliary variables for the squared terms
        // We want to minimize (x-5)^2 + (y-3)^2

        // Create variables for (x-5) and (y-3)
        final var xDiff = model.newIntVar(-5, 5, "x-5");  // x is [0,10], so x-5 is [-5,5]
        final var yDiff = model.newIntVar(-3, 7, "y-3");  // y is [0,10], so y-3 is [-3,7]

        model.addEquality(LinearExpr.newBuilder().add(x).add(-5), xDiff);
        model.addEquality(LinearExpr.newBuilder().add(y).add(-3), yDiff);

        // Create variables for the squared terms
        final var xSquared = model.newIntVar(0, 25, "xSquared");  // max is 5^2 = 25
        final var ySquared = model.newIntVar(0, 49, "ySquared");  // max is 7^2 = 49

        // Add multiplication constraints for squaring
        model.addMultiplicationEquality(xSquared, new IntVar[]{xDiff, xDiff});
        model.addMultiplicationEquality(ySquared, new IntVar[]{yDiff, yDiff});

        // Minimize the sum of squared distances
        model.minimize(LinearExpr.sum(new IntVar[]{xSquared, ySquared}));

        logger.info("Objective: Minimize (x-5)^2 + (y-3)^2");

        // Solve the model
        final var solver = new CpSolver();
        logger.info("Solving...");
        final var status = solver.solve(model);

        // Check that the problem has an optimal solution
        if (status == CpSolverStatus.OPTIMAL || status == CpSolverStatus.FEASIBLE) {
            logger.info("Solution found!");
            final var xVal = solver.value(x);
            final var yVal = solver.value(y);

            logger.info(String.format("x = %d", xVal));
            logger.info(String.format("y = %d", yVal));

            // Calculate and display the objective value
            final var objValue = solver.objectiveValue();
            logger.info(String.format("Objective value = %.0f", objValue));

            // Manual verification
            final var xDiffVal = xVal - 5;
            final var yDiffVal = yVal - 3;
            final var manualObj = xDiffVal * xDiffVal + yDiffVal * yDiffVal;
            logger.info(String.format("Verification: (%d)^2 + (%d)^2 = %d",
                    xDiffVal, yDiffVal, manualObj));

            // Verify constraints
            logger.info("Constraint verification:");
            logger.info(String.format("  x + y = %d (>= 10? %s)",
                    xVal + yVal, (xVal + yVal >= 10) ? "✓" : "✗"));
            logger.info(String.format("  x + 2*y = %d (<= 14? %s)",
                    xVal + 2 * yVal, (xVal + 2 * yVal <= 14) ? "✓" : "✗"));

            logger.info("Solver statistics:");
            logger.info("  Status: " + status);
            logger.info(String.format("  Wall time: %s seconds", solver.wallTime()));
            logger.info(String.format("  Branches: %d", solver.numBranches()));
            logger.info(String.format("  Conflicts: %d", solver.numConflicts()));
        } else {
            logger.error("No solution found!");
            logger.error("Status: " + status);
        }
    }
}

