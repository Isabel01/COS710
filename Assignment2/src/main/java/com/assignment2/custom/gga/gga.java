package com.assignment2.custom.gga;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.Collections;
import java.util.List;


/**
 * Created by chris on 2016/03/28.
 */
public class gga<S extends Solution<?>> extends GenerationalGeneticAlgorithm<S> {
    double elitsm;
    /**
     * Constructor
     *
     * @param problem
     * @param maxEvaluations
     * @param populationSize
     * @param crossoverOperator
     * @param mutationOperator
     * @param selectionOperator
     * @param evaluator
     */
    public gga(Problem<S> problem, int maxEvaluations, int populationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
    }

    public void setElitist(double percentage) {
        this.elitsm = percentage;
    }

    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        double size = offspringPopulation.size();
        double replacement = size*(elitsm/100);
        int count = (int) Math.floor(replacement);

        ObjectiveComparator comparator = new ObjectiveComparator<S>(0);
        Collections.sort(population, comparator);
        Collections.sort(offspringPopulation, comparator) ;

        for(int i = 0; i < count; i++) {
            offspringPopulation.remove(offspringPopulation.size()-1);
        }

        for(int i = 0; i < count; i++) {
            offspringPopulation.add(population.get(i));
        }

        return offspringPopulation;
    }
}
