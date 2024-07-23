package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByNameActionTest {

    @Test
    void whenItemWasFindSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        String findName = "Find item";
        Item expected = new Item(findName);
        tracker.add(expected);
        FindByNameAction findByNameAction = new FindByNameAction(output);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(findName);
        findByNameAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find items by name ===" + ln + expected + ln);
    }

    @Test
    void whenItemWasFindUnsuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        String findName = "Find item";
        String unFindName = "UnFind item";
        Item expected = new Item(findName);
        tracker.add(expected);
        FindByNameAction findByNameAction = new FindByNameAction(output);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(unFindName);
        findByNameAction.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo("=== Find items by name ==="
                + ln + "Заявки с именем: " +  unFindName + " не найдены." + ln);
    }
}