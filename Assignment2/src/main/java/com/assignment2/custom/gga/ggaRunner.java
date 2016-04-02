package com.assignment2.custom.gga;

import com.assignment2.custom.gaBuilder;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.Sphere;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 2016/03/29.
 */
public class ggaRunner {
    public static void main(String[] args) throws Exception {
        Algorithm<DoubleSolution> algorithm;
        DoubleProblem problem = new Sphere(30) ;
        double elitismRate = 10.0;

        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(0.9, 10.0) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(problem, 10.0) ;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<>();

        algorithm = new gaBuilder<>(problem, crossoverOperator, mutationOperator, elitismRate)
                .setPopulationSize(100)
                .setMaxEvaluations(25000)
                .setSelectionOperator(selectionOperator)
                .build() ;

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute() ;

        DoubleSolution solution = algorithm.getResult() ;
        List<DoubleSolution> population = new ArrayList<>(1) ;
        population.add(solution) ;

        long computingTime = algorithmRunner.getComputingTime() ;

        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

    }
}
