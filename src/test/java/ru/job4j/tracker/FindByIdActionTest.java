package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByIdActionTest {

    @Test
    void whenItemWasFindSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item expected = new Item("Find item");
        tracker.add(expected);
        FindByIdAction findByIdAction = new FindByIdAction(output);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        findByIdAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find item by id ===" + ln + expected + ln);
    }

    @Test
    void whenItemWasFindUnSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        int id = 2;
        Item expected = new Item("Find  item");
        tracker.add(expected);
        FindByIdAction findByIdAction = new FindByIdAction(output);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(id);
        findByIdAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find item by id ==="
                + ln + "Заявка с введенным id: " + id + " не найдена." + ln);
    }
}