/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.dataquality.indicators;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.dataquality.indicators.IndicatorsPackage
 * @generated
 */
public interface IndicatorsFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    IndicatorsFactory eINSTANCE = org.talend.dataquality.indicators.impl.IndicatorsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Indicator</em>'.
     * @generated
     */
    Indicator createIndicator();

    /**
     * Returns a new object of class '<em>Row Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Row Count Indicator</em>'.
     * @generated
     */
    RowCountIndicator createRowCountIndicator();

    /**
     * Returns a new object of class '<em>Mean Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Mean Indicator</em>'.
     * @generated
     */
    MeanIndicator createMeanIndicator();

    /**
     * Returns a new object of class '<em>Sum Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Sum Indicator</em>'.
     * @generated
     */
    SumIndicator createSumIndicator();

    /**
     * Returns a new object of class '<em>Composite Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Composite Indicator</em>'.
     * @generated
     */
    CompositeIndicator createCompositeIndicator();

    /**
     * Returns a new object of class '<em>Range Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Range Indicator</em>'.
     * @generated
     */
    RangeIndicator createRangeIndicator();

    /**
     * Returns a new object of class '<em>Box Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Box Indicator</em>'.
     * @generated
     */
    BoxIndicator createBoxIndicator();

    /**
     * Returns a new object of class '<em>Indicator Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Indicator Type</em>'.
     * @generated
     */
    IndicatorType createIndicatorType();

    /**
     * Returns a new object of class '<em>Frequency Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Frequency Indicator</em>'.
     * @generated
     */
    FrequencyIndicator createFrequencyIndicator();

    /**
     * Returns a new object of class '<em>Blank Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Blank Count Indicator</em>'.
     * @generated
     */
    BlankCountIndicator createBlankCountIndicator();

    /**
     * Returns a new object of class '<em>Indicator Parameters</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Indicator Parameters</em>'.
     * @generated
     */
    IndicatorParameters createIndicatorParameters();

    /**
     * Returns a new object of class '<em>Median Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Median Indicator</em>'.
     * @generated
     */
    MedianIndicator createMedianIndicator();

    /**
     * Returns a new object of class '<em>Value Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Value Indicator</em>'.
     * @generated
     */
    ValueIndicator createValueIndicator();

    /**
     * Returns a new object of class '<em>Min Value Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Min Value Indicator</em>'.
     * @generated
     */
    MinValueIndicator createMinValueIndicator();

    /**
     * Returns a new object of class '<em>Max Value Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Max Value Indicator</em>'.
     * @generated
     */
    MaxValueIndicator createMaxValueIndicator();

    /**
     * Returns a new object of class '<em>Mode Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Mode Indicator</em>'.
     * @generated
     */
    ModeIndicator createModeIndicator();

    /**
     * Returns a new object of class '<em>Null Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Null Count Indicator</em>'.
     * @generated
     */
    NullCountIndicator createNullCountIndicator();

    /**
     * Returns a new object of class '<em>Distinct Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Distinct Count Indicator</em>'.
     * @generated
     */
    DistinctCountIndicator createDistinctCountIndicator();

    /**
     * Returns a new object of class '<em>Unique Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Unique Count Indicator</em>'.
     * @generated
     */
    UniqueCountIndicator createUniqueCountIndicator();

    /**
     * Returns a new object of class '<em>Duplicate Count Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Duplicate Count Indicator</em>'.
     * @generated
     */
    DuplicateCountIndicator createDuplicateCountIndicator();

    /**
     * Returns a new object of class '<em>IQR Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>IQR Indicator</em>'.
     * @generated
     */
    IQRIndicator createIQRIndicator();

    /**
     * Returns a new object of class '<em>Text Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Text Indicator</em>'.
     * @generated
     */
    TextIndicator createTextIndicator();

    /**
     * Returns a new object of class '<em>Min Length Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Min Length Indicator</em>'.
     * @generated
     */
    MinLengthIndicator createMinLengthIndicator();

    /**
     * Returns a new object of class '<em>Max Length Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Max Length Indicator</em>'.
     * @generated
     */
    MaxLengthIndicator createMaxLengthIndicator();

    /**
     * Returns a new object of class '<em>Average Length Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Average Length Indicator</em>'.
     * @generated
     */
    AverageLengthIndicator createAverageLengthIndicator();

    /**
     * Returns a new object of class '<em>Length Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Length Indicator</em>'.
     * @generated
     */
    LengthIndicator createLengthIndicator();

    /**
     * Returns a new object of class '<em>Text Parameters</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Text Parameters</em>'.
     * @generated
     */
    TextParameters createTextParameters();

    /**
     * Returns a new object of class '<em>Lower Quartile Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Lower Quartile Indicator</em>'.
     * @generated
     */
    LowerQuartileIndicator createLowerQuartileIndicator();

    /**
     * Returns a new object of class '<em>Upper Quartile Indicator</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Upper Quartile Indicator</em>'.
     * @generated
     */
    UpperQuartileIndicator createUpperQuartileIndicator();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    IndicatorsPackage getIndicatorsPackage();

} //IndicatorsFactory
