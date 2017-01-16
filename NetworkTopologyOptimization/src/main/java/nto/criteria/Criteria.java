package nto.criteria;

public interface Criteria<T extends CriteriaCalculationContext> {

	public CriteriaCalculationResult calculate(T context);

}
