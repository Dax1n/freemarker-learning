package freemarker.core.exception;

import freemarker.core.Environment;
import freemarker.core._ErrorDescriptionBuilder;
import freemarker.core.nodes.Expression;
import freemarker.template.template_model.TemplateModel;
import freemarker.template.template_model.TemplateModelException;
import freemarker.template.utility.ClassUtil;

public class _TemplateModelException extends TemplateModelException {

    // Note: On Java 5 we will use `String descPart1, Object... furtherDescParts` instead of `Object[] descriptionParts`
    //       and `String description`. That's why these are at the end of the parameter list.
    
    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:
    
    public _TemplateModelException(String description) {
        super(description);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:

    public _TemplateModelException(Throwable cause, String description) {
        this(cause, null, description);
    }

    public _TemplateModelException(Environment env, String description) {
        this((Throwable) null, env, description);
    }
    
    public _TemplateModelException(Throwable cause, Environment env) {
        this(cause, env, (String) null);
    }

    public _TemplateModelException(Throwable cause) {
        this(cause, null, (String) null);
    }
    
    public _TemplateModelException(Throwable cause, Environment env, String description) {
        super(cause, env, description, true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:
    
    public _TemplateModelException(_ErrorDescriptionBuilder description) {
        this(null, description);
    }

    public _TemplateModelException(Environment env, _ErrorDescriptionBuilder description) {
        this(null, env, description);
    }

    public _TemplateModelException(Throwable cause, Environment env, _ErrorDescriptionBuilder description) {
        super(cause, env, description, true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:
    
    public _TemplateModelException(Object[] descriptionParts) {
        this((Environment) null, descriptionParts);
    }

    public _TemplateModelException(Environment env, Object[] descriptionParts) {
        this((Throwable) null, env, descriptionParts);
    }

    public _TemplateModelException(Throwable cause, Object[] descriptionParts) {
        this(cause, null, descriptionParts);
    }

    public _TemplateModelException(Throwable cause, Environment env, Object[] descriptionParts) {
        super(cause, env, new _ErrorDescriptionBuilder(descriptionParts), true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:
    
    public _TemplateModelException(Expression blamed, Object[] descriptionParts) {
        this(blamed, null, descriptionParts);
    }

    public _TemplateModelException(Expression blamed, Environment env, Object[] descriptionParts) {
        this(blamed, null, env, descriptionParts);
    }

    public _TemplateModelException(Expression blamed, Throwable cause, Environment env, Object[] descriptionParts) {
        super(cause, env, new _ErrorDescriptionBuilder(descriptionParts).blame(blamed), true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Permutation group:
    
    public _TemplateModelException(Expression blamed, String description) {
        this(blamed, null, description);
    }

    public _TemplateModelException(Expression blamed, Environment env, String description) {
        this(blamed, null, env, description);
    }

    public _TemplateModelException(Expression blamed, Throwable cause, Environment env, String description) {
        super(cause, env, new _ErrorDescriptionBuilder(description).blame(blamed), true);
    }

    public static Object[] modelHasStoredNullDescription(Class expected, TemplateModel model) {
        return new Object[] {
                "The FreeMarker value exists, but has nothing inside it; the TemplateModel object (class: ",
                model.getClass().getName(), ") has returned a null instead of a ",
                ClassUtil.getShortClassName(expected), ". ",
                "This is possibly a bug in the non-FreeMarker code that builds the data-model." };
    }
    
}
