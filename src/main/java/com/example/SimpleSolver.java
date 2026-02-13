package com.example;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

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

    public static void main(String[] args) {
        // Load the OR-Tools native library
        Loader.loadNativeLibraries();

        System.out.println("=== Google OR-Tools Linear Programming Example ===\n");

        // Create the linear solver with the GLOP backend
        MPSolver solver = MPSolver.createSolver("GLOP");
        if (solver == null) {
            System.err.println("Could not create solver GLOP");
            return;
        }

        // Create the variables x and y with bounds [0, infinity)
        double infinity = java.lang.Double.POSITIVE_INFINITY;
        MPVariable x = solver.makeNumVar(0.0, infinity, "x");
        MPVariable y = solver.makeNumVar(0.0, infinity, "y");

        System.out.println("Number of variables = " + solver.numVariables());

        // Create the constraints
        // x + 2y <= 14
        MPConstraint c0 = solver.makeConstraint(-infinity, 14.0, "c0");
        c0.setCoefficient(x, 1);
        c0.setCoefficient(y, 2);

        // 3x - y >= 0
        MPConstraint c1 = solver.makeConstraint(0.0, infinity, "c1");
        c1.setCoefficient(x, 3);
        c1.setCoefficient(y, -1);

        // x - y <= 2
        MPConstraint c2 = solver.makeConstraint(-infinity, 2.0, "c2");
        c2.setCoefficient(x, 1);
        c2.setCoefficient(y, -1);

        System.out.println("Number of constraints = " + solver.numConstraints());

        // Create the objective function: 3x + 4y
        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 3);
        objective.setCoefficient(y, 4);
        objective.setMaximization();

        // Solve the system
        System.out.println("\nSolving...\n");
        final MPSolver.ResultStatus resultStatus = solver.solve();

        // Check that the problem has an optimal solution
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Solution found!");
            System.out.println("Objective value = " + objective.value());
            System.out.println("x = " + x.solutionValue());
            System.out.println("y = " + y.solutionValue());

            System.out.println("\nAdvanced usage:");
            System.out.println("Problem solved in " + solver.wallTime() + " milliseconds");
            System.out.println("Problem solved in " + solver.iterations() + " iterations");
        } else {
            System.err.println("The problem does not have an optimal solution!");
        }
    }
}
