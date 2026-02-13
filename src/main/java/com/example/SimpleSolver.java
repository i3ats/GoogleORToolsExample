package com.example;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple example demonstrating Google OR-Tools.
 * This example solves a linear programming problem:
 *
 * Maximize: 3x + 4y
 * Subject to:
 *   x + 2y <= 14
 *   3x - y >= 0
 *   x - y <= 2
 *   x, y >= 0
 */
public class SimpleSolver {
    private static final Logger logger = LoggerFactory.getLogger(SimpleSolver.class);

    public static void main(String[] args) {
        // Load the OR-Tools native library
        Loader.loadNativeLibraries();

        logger.info("=== Google OR-Tools Linear Programming Example ===");

        // Create the linear solver with the GLOP backend
        final var solver = MPSolver.createSolver("GLOP");
        if (solver == null) {
            logger.error("Could not create solver GLOP");
            return;
        }

        // Create the variables x and y with bounds [0, infinity)
        final var infinity = java.lang.Double.POSITIVE_INFINITY;
        final var x = solver.makeNumVar(0.0, infinity, "x");
        final var y = solver.makeNumVar(0.0, infinity, "y");

        logger.info("Number of variables = " + solver.numVariables());

        // Create the constraints
        // x + 2y <= 14
        final var c0 = solver.makeConstraint(-infinity, 14.0, "c0");
        c0.setCoefficient(x, 1);
        c0.setCoefficient(y, 2);

        // 3x - y >= 0
        final var c1 = solver.makeConstraint(0.0, infinity, "c1");
        c1.setCoefficient(x, 3);
        c1.setCoefficient(y, -1);

        // x - y <= 2
        final var c2 = solver.makeConstraint(-infinity, 2.0, "c2");
        c2.setCoefficient(x, 1);
        c2.setCoefficient(y, -1);

        logger.info("Number of constraints = " + solver.numConstraints());

        // Create the objective function: 3x + 4y
        final var objective = solver.objective();
        objective.setCoefficient(x, 3);
        objective.setCoefficient(y, 4);
        objective.setMaximization();

        // Solve the system
        logger.info("Solving...");
        final var resultStatus = solver.solve();

        // Check that the problem has an optimal solution
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            logger.info("Solution found!");
            logger.info("Objective value = " + objective.value());
            logger.info("x = " + x.solutionValue());
            logger.info("y = " + y.solutionValue());

            logger.info("Advanced usage:");
            logger.info("Problem solved in " + solver.wallTime() + " milliseconds");
            logger.info("Problem solved in " + solver.iterations() + " iterations");
        } else {
            logger.error("The problem does not have an optimal solution!");
        }
    }
}
