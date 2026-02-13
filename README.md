# GoogleORToolsExample
Simple examples using Google OR-Tools

## Overview
This project demonstrates how to use Google OR-Tools in Java with Gradle. OR-Tools is a powerful optimization library for solving linear programming, constraint programming, and other optimization problems.

## Examples

### 1. SimpleSolver - Linear Programming
The `SimpleSolver` class demonstrates solving a linear programming problem using the GLOP solver:

**Objective:** Maximize `3x + 4y`

**Subject to:**
- `x + 2y ≤ 14`
- `3x - y ≥ 0`
- `x - y ≤ 2`
- `x, y ≥ 0`

### 2. QuadraticSolver - Quadratic Optimization
The `QuadraticSolver` class demonstrates solving a quadratic optimization problem using the CP-SAT solver:

**Objective:** Minimize `(x-4)² + (y-3)² + (z-5)²`

**Subject to:**
- `x + y + z ≥ 10`
- `x + 2y ≤ 20`
- `0 ≤ x, y, z ≤ 10`

This problem finds the integer point closest to (4, 3, 5) that satisfies the constraints.

## Prerequisites
- Java 11 or higher
- Gradle (wrapper included)

## Building
```bash
./gradlew build
```

## Running

### Run the Linear Programming Example
```bash
./gradlew run
```

Or on Windows:
```bash
.\gradlew.bat run
```

### Run the Quadratic Optimization Example
```bash
./gradlew runQuadraticSolver
```

Or on Windows:
```bash
.\gradlew.bat runQuadraticSolver
```

## Expected Output

### SimpleSolver Output
The program will find the optimal solution and display:
- The optimal values of x and y (approximately 6.0 and 4.0)
- The maximum objective function value (approximately 34)
- Solver statistics (time, iterations)

### QuadraticSolver Output
The program will find the optimal solution and display:
- The optimal integer values of x, y, and z
- The minimum objective function value
- Constraint verification
- Solver statistics (time, branches, conflicts)

## Dependencies
- Google OR-Tools 9.8.3296
- Java 11 or higher
- Gradle 8.10.2 (wrapper included)

## Project Structure
```
GoogleORToolsExample/
├── build.gradle.kts
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   ├── SimpleSolver.java
│                   ├── QuadraticSolver.java
│                   └── LoggingConfig.java
└── README.md
```

## Key Concepts

### Linear Programming (SimpleSolver)
Uses the GLOP solver for continuous variable optimization. Ideal for problems with linear objectives and linear constraints.

### Quadratic Programming (QuadraticSolver)
Uses the CP-SAT solver for integer variable optimization with quadratic objectives. The CP-SAT solver handles quadratic objectives by using auxiliary variables and multiplication constraints to represent squared terms.

## Learn More
- [Google OR-Tools Documentation](https://developers.google.com/optimization)
- [CP-SAT Solver](https://developers.google.com/optimization/cp/cp_solver)
- [Linear Optimization](https://developers.google.com/optimization/lp)

