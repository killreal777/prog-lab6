package creators;

import io.Terminal;
import io.TextFormatter;
import subject.model.FieldDefinitionException;


/**
 * Abstract class for creating and validation composite data objects
 *
 * @param <CreatingObject> composite data object
 */


public abstract class Creator<CreatingObject> {
    protected final Terminal terminal;
    protected CreatingObject creatingObject;


    protected Creator(Terminal terminal) {
        this.terminal = terminal;
    }


    public CreatingObject create() {
        try {
            initCreatingObjectIfItsNecessary();
            defineFields();     // abstract
            return returnAndReset();
        } catch (FieldDefinitionException e) {
            terminal.print(e.getMessage());
            return create();
        }
    }

    private void initCreatingObjectIfItsNecessary() {
        if (creatingObject == null)
            creatingObject = createNewInstance();   // abstract
    }

    private CreatingObject returnAndReset() {
        CreatingObject definedObject = creatingObject;
        creatingObject = null;
        return definedObject;
    }


    protected void checkArgumentsAmount(String[] args, int correctAmount) {
        if (args.length != correctAmount)
            throw new FieldDefinitionException(String.format("Неверное число аргументов (введено: %s, ожидалось: %s)",
                    args.length, correctAmount));
    }

    protected String formatRequirements(String requirements) {
        requirements = "(" + requirements + ") ";
        return TextFormatter.format(requirements, TextFormatter.Format.GRAY_ITALIC);
    }


    protected abstract CreatingObject createNewInstance();

    protected abstract void defineFields() throws FieldDefinitionException;
}
