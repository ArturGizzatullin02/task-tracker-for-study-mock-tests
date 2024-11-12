package dev.gizzatullin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskTrackerApplicationTests extends AbstractContainerBaseTest {

    @Test
    @DisplayName(value = "Контекст инициализирован успешно")
    void contextLoads() {
    }
}
