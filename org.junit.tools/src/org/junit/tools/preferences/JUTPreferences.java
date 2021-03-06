package org.junit.tools.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.junit.tools.Activator;

/**
 * Preference-class for the junit-tools-processing.
 * 
 * @author Robert Streng
 * 
 */
public class JUTPreferences implements IJUTPreferenceConstants {

	// from main-page
	private static Boolean writeTML = null;

	private static String tmlContainer = null;

	private static String testProjectPostfix = null;

	private static String testSourceFolderName = null;

	private static String testPackagePostfix = null;

	private static String testMethodPrefix = null;

	private static String testMethodPostfix = null;

	private static String testClassSuperType = null;

	private static String testClassPrefix = null;

	private static String testClassPostfix = null;

	private static String mockProject = null;
	
	private static Boolean mockSaveInTestProject = null;

	private static String mockFramework = null;
	
	// from annotations-page
	private static String[] testClassAnnotations = null;

	private static String[] mockClassAnnotations = null;

	// from filter-page
	private static String[] testMethodFilterName = null;

	private static String[] testMethodFilterModifier = null;

	// from static-bindings-page
	private static String[] staticBindings = null;
	private static Map<String, String> staticBindingsMapBase = null;
	private static Map<String, String> staticBindingsMapTest = null;

	public static boolean getPreferenceBoolean(String name) {
		return getPreferenceStore().getBoolean(name);
	}

	public static String getPreference(String name) {
		return getPreferenceStore().getString(name);
	}

	private static IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	public static String getTmlContainer() {
		if (tmlContainer == null) {
			tmlContainer = getPreference(TML_CONTAINER);
		}
		return tmlContainer;
	}

	public static boolean isWriteTML() {
		if (writeTML == null) {
			writeTML = getPreferenceBoolean(WRITE_TML);
		}
		return writeTML;
	}

	public static String getTestProjectPostfix() {
		if (testProjectPostfix == null) {
			testProjectPostfix = getPreference(TEST_PROJECT_POSTFIX);
		}
		return testProjectPostfix;
	}

	public static String getTestSourceFolderName() {
		if (testSourceFolderName == null) {
			testSourceFolderName = getPreference(TEST_SOURCE_FOLDER_NAME);
		}
		return testSourceFolderName;
	}

	public static String getTestMethodPostfix() {
		if (testMethodPostfix == null) {
			testMethodPostfix = getPreference(TEST_METHOD_POSTFIX);
		}
		return testMethodPostfix;
	}

	protected static void setMockClassAnnotations(String[] mockClassAnnotations) {
		JUTPreferences.mockClassAnnotations = mockClassAnnotations;
	}

	protected static void setTestClassAnnotations(String[] testClassAnnotations) {
		JUTPreferences.testClassAnnotations = testClassAnnotations;
	}

	public static String[] getTestClassAnnotations() {
		if (testClassAnnotations == null) {
			testClassAnnotations = convert(getPreference(TEST_CLASS_ANNOTATIONS));
		}
		return testClassAnnotations;
	}

	public static String[] getMockClassAnnotations() {
		if (mockClassAnnotations == null) {
			mockClassAnnotations = convert(getPreference(MOCK_CLASS_ANNOTATIONS));
		}
		return mockClassAnnotations;
	}

	protected static void setStaticBindings(String[] staticBindings) {
		JUTPreferences.staticBindings = staticBindings;
		initStaticBindingsMaps();
	}

	protected static void setTestMethodFilterName(String[] testMethodFilterName) {
		JUTPreferences.testMethodFilterName = testMethodFilterName;
	}

	protected static void setTestMethodFilterModifier(
			String[] testmethodFilterModifier) {
		JUTPreferences.testMethodFilterModifier = testmethodFilterModifier;
	}

	public static String[] getTestMethodFilterName() {
		if (testMethodFilterName == null) {
			testMethodFilterName = convert(getPreference(TEST_METHOD_FILTER_NAME));
		}
		return testMethodFilterName;
	}

	public static String[] getTestMethodFilterModifier() {
		if (testMethodFilterModifier == null) {
			testMethodFilterModifier = convert(getPreference(TEST_METHOD_FILTER_MODIFIER));
		}
		return testMethodFilterModifier;
	}

	public static String[] getStaticBindings() {
		if (staticBindings == null) {
			staticBindings = convert(getPreference(STATIC_BINDINGS));
		}
		return staticBindings;
	}

	private static void initStaticBindingsMaps() {
		if (staticBindings == null) {
			staticBindings = getStaticBindings();
		}

		staticBindingsMapBase = new HashMap<String, String>();
		staticBindingsMapTest = new HashMap<String, String>();

		for (String staticBinding : staticBindings) {
			String baseProject, testProject;
			String[] projects = staticBinding.split(LIST_ENTRY_SEPERATOR);

			if (projects.length == 2) {
				baseProject = projects[0];
				testProject = projects[1];

				staticBindingsMapBase.put(baseProject, testProject);
				staticBindingsMapTest.put(testProject, baseProject);
			}
		}
	}

	public static Map<String, String> getStaticBindingsMapBaseProject() {
		if (staticBindingsMapBase == null) {
			initStaticBindingsMaps();
		}

		return staticBindingsMapBase;
	}

	public static Map<String, String> getStaticBindingsMapTestProject() {
		if (staticBindingsMapTest == null) {
			initStaticBindingsMaps();
		}

		return staticBindingsMapTest;
	}

	protected static void setTmlContainer(String tmlContainerPref) {
		JUTPreferences.tmlContainer = tmlContainerPref;
	}

	protected static void setWriteTML(Boolean writeTML) {
		JUTPreferences.writeTML = writeTML;
	}

	protected static void setTestProjectPostfix(String testProjectPostfixPref) {
		JUTPreferences.testProjectPostfix = testProjectPostfixPref;
	}

	protected static void setTestSourceFolderName(String testSourceFolderName) {
		JUTPreferences.testSourceFolderName = testSourceFolderName;
	}

	protected static void setTestMethodPostfix(String testmethodPostfixPref) {
		JUTPreferences.testMethodPostfix = testmethodPostfixPref;
	}

	public static String getTestMethodPrefix() {
		if (testMethodPrefix == null)
			testMethodPrefix = getPreference(TEST_METHOD_PREFIX);
		return testMethodPrefix;
	}

	protected static void setTestMethodPrefix(String testMethodPrefix) {
		JUTPreferences.testMethodPrefix = testMethodPrefix;
	}

	public static String getTestClassSuperType() {
		if (testClassSuperType == null) {
			testClassSuperType = getPreference(TEST_CLASS_SUPER_TYPE);
		}
		return testClassSuperType;
	}

	protected static void setTestClassSuperType(String testClassSuperType) {
		JUTPreferences.testClassSuperType = testClassSuperType;
	}

	protected static void setTestPackagePostfix(String newValue) {
		JUTPreferences.testPackagePostfix = newValue;
	}

	public static String getTestPackagePostfix() {
		if (testPackagePostfix == null) {
			testPackagePostfix = getPreference(TEST_PACKAGE_POSTFIX);
		}
		return testPackagePostfix;
	}

	protected static void setTestClassPrefix(String newValue) {
		JUTPreferences.testClassPrefix = newValue;
	}

	public static String getTestClassPrefix() {
		if (testClassPrefix == null) {
			testClassPrefix = getPreference(TEST_CLASS_PREFIX);
		}
		return testClassPrefix;
	}

	protected static void setTestClassPostfix(String newValue) {
		JUTPreferences.testClassPostfix = newValue;
	}

	public static String getTestClassPostfix() {
		if (testClassPostfix == null) {
			testClassPostfix = getPreference(TEST_CLASS_POSTFIX);
		}
		return testClassPostfix;
	}

	public static String getMockProject() {
		if (mockProject == null) {
			mockProject = getPreference(MOCK_PROJECT);
		}
		return mockProject;
	}

	public static Boolean isMockSaveInTestProject() {
		if (mockSaveInTestProject == null) {
			mockSaveInTestProject = getPreferenceBoolean(MOCK_SAVE_IN_TESTPROJECT);
		}
		return mockSaveInTestProject;
	}
	
	public static void setMockProject(String mockProject) {
		JUTPreferences.mockProject = mockProject;
	}
	
	public static String getMockFramework() {
		return mockFramework;
	}
	
	public static void setMockSaveInTestProject(Boolean mockSaveInTestProject) {
		JUTPreferences.mockSaveInTestProject = mockSaveInTestProject;
	}
	
	public static void setMockFramework(String mockFramework) {
		JUTPreferences.mockFramework= mockFramework;
	}
	
	/**
	 * Converter for lists
	 * 
	 * @param value
	 * @return Array with list-entries
	 */
	public static String[] convert(String value) {
		StringTokenizer tokenizer = new StringTokenizer(value, LIST_DELIMITER);
		int tokenCount = tokenizer.countTokens();
		String[] elements = new String[tokenCount];

		for (int i = 0; i < tokenCount; i++) {
			elements[i] = tokenizer.nextToken();
		}

		return elements;
	}

	/**
	 * Converter for lists
	 * 
	 * @return value
	 */
	public static String convert(String[] values) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			buffer.append(values[i]);
			buffer.append(LIST_DELIMITER);
		}

		return buffer.toString();
	}

	public static void initialize() {
		getPreferenceStore().addPropertyChangeListener(
				new IPropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty() == TEST_PROJECT_POSTFIX) {
							setTestProjectPostfix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_SOURCE_FOLDER_NAME) {
							setTestSourceFolderName((String) event
									.getNewValue());
							return;
						} else if (event.getProperty() == TML_CONTAINER) {
							setTmlContainer((String) event.getNewValue());
							return;
						} else if (event.getProperty() == WRITE_TML) {
							setWriteTML((Boolean) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_PACKAGE_POSTFIX) {
							setTestPackagePostfix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_METHOD_PREFIX) {
							setTestMethodPrefix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_METHOD_POSTFIX) {
							setTestMethodPostfix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_METHOD_FILTER_MODIFIER) {
							setTestMethodFilterModifier(convert((String) event
									.getNewValue()));
							return;
						} else if (event.getProperty() == TEST_CLASS_SUPER_TYPE) {
							setTestClassSuperType((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_CLASS_PREFIX) {
							setTestClassPrefix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == TEST_CLASS_POSTFIX) {
							setTestClassPostfix((String) event.getNewValue());
							return;
						} else if (event.getProperty() == MOCK_PROJECT) {
							setMockProject((String) event.getNewValue());
							return;
						} else if (event.getProperty() == MOCK_SAVE_IN_TESTPROJECT) {
							setMockSaveInTestProject((Boolean) event.getNewValue());
							return;
						} else if (event.getProperty() == MOCK_FRAMEWORK) {
							setMockFramework((String) event.getNewValue());
							return;							
						} else if (event.getProperty() == TEST_CLASS_ANNOTATIONS) {
							setTestClassAnnotations(convert((String) event
									.getNewValue()));
							return;
						} else if (event.getProperty() == MOCK_CLASS_ANNOTATIONS) {
							setMockClassAnnotations(convert((String) event
									.getNewValue()));
							return;
						} else if (event.getProperty() == STATIC_BINDINGS) {
							setStaticBindings(convert((String) event
									.getNewValue()));
							return;
						}
					}

				});
	}

}
