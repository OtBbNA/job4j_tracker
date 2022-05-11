package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StartUI {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Tracker tracker, ArrayList<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }

    private void showMenu(ArrayList<UserAction> actions) {
        out.println("Menu:");
        for (UserAction useraction : actions) {
            out.println(actions.indexOf(useraction) + ". " + actions.get(actions.indexOf(useraction)).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        Tracker tracker = new Tracker();
        ArrayList<UserAction> actions = new ArrayList<>();
        Collections.addAll(actions,  new CreateAction(output), new ShowAllItems(output), new EditItem(output),
                new DeleteItem(output), new FindByID(output), new FindByName(output), new ExitProgram(output));
        new StartUI(output).init(input, tracker, actions);
    }
}
