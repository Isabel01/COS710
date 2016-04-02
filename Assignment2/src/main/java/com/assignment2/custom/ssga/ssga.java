package com.assignment2.custom.ssga;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import java.util.Collections;
import java.util.List;

/**
 * Created by chris on 2016/03/28.
 */
public class ssga<S extends Solution<?>> extends SteadyStateGeneticAlgorithm<S> {

    String replacement;
    /**
     * Constructor
     *
     * @param problem
     * @param maxEvaluations
     * @param populationSize
     * @param crossoverOperator
     * @param mutationOperator
     * @param selectionOperator
     */
    public ssga(Problem<S> problem, int maxEvaluations, int populationSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator, SelectionOperator<List<S>, S> selectionOperator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator);
    }

    public void setReplacement(String r) {
        replacement = r;
    }

    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        if(replacement.equals(new String("worst")))
            return replaceWorst(population, offspringPopulation);
        if(replacement.equals(new String("random")))
            return replaceRandom(population, offspringPopulation);
        if(replacement.equals(new String("tournament")))
            return replaceTournament(population, offspringPopulation);
        else return null;
    }

    private List<S> replaceWorst(List<S> population, List<S> offspringPopulation) {
        ObjectiveComparator comparator = new ObjectiveComparator<S>(0);
        Collections.sort(population, comparator) ;
        int worstSolutionIndex = population.size() - 1;
        if (comparator.compare(population.get(worstSolutionIndex), offspringPopulation.get(0)) > 0) {
            population.remove(worstSolutionIndex);
            population.add(offspringPopulation.get(0));
        }
        return population;
    }

    private List<S> replaceRandom(List<S> population, List<S> offspringPopulation) {
        ObjectiveComparator comparator = new ObjectiveComparator<S>(0);
        Collections.sort(population, comparator) ;
        int max = population.size()-1;
        int min = 0;
        int randomNum = min + (int) (Math.random() * ((max - min) +1));
        if (comparator.compare(population.get(randomNum), offspringPopulation.get(0)) > 0) {
            population.remove(randomNum);
            population.add(offspringPopulation.get(0));
        }
        return population;
    }

    private List<S> replaceTournament(List<S> population, List<S> offspringPopulation) {
        ObjectiveComparator comparator = new ObjectiveComparator<S>(0);
        Collections.sort(population, comparator) ;
        double prob = Math.random();
        int max = population.size()-1;
        int min = 0;
        int contestant1 = min + (int) (Math.random() * ((max - min) +1));
        int contestant2 = min + (int) (Math.random() * ((max - min) +1));
        while (contestant1 == contestant2) {
            contestant2 = min + (int) (Math.random() * ((max - min) +1));
        }
        if(prob < 0.5)
            return population;

        if (comparator.compare(population.get(contestant1), population.get(contestant2)) > 0) {
            population.remove(contestant1);
            population.add(offspringPopulation.get(0));
        } else {
            population.remove(contestant2);
            population.add(offspringPopulation.get(0));
        }
        return population;
    }

}
