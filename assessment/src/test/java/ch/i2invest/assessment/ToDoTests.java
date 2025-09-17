package ch.i2invest.assessment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
Description

    Tests run against a simple TODO app accessible here: https://todomvc.com/examples/javascript-es6/dist/.

    Feel free to play around with it and explore its functionality.
    Note: The app is stateless — TODOs do not persist. Each time you open the page, you get a fresh TODO list.
*/

/*
    Your tasks

        1. There are three unit tests already provided. One of them fails; your task is to identify and fix it.

        2. Write a unit test to verify that a todo’s text can be edited.

        3. Write additional unit tests for any other use cases you consider important. Focus on the core functionality of a TODO app first.


    Time: 30 minutes.
    Internet: Use of internet is allowed, except for the use of chatgpt and similar LLMs.
*/

public class ToDoTests {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // modern headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://todomvc.com/examples/javascript-es6/dist/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addNewTodo() {
        // given (page todo page loads with a todo input)
        WebElement newTodoInput = driver.findElement(By.className("new-todo"));
        String todoText = "meet the i2 team";

        // when (a todo is added)
        newTodoInput.sendKeys(todoText + "\n");

        // then (the added todo appears in the list)
        WebElement firstTodo = driver.findElement(By.cssSelector(".todo-list li"));
        Assert.assertEquals(firstTodo.getText(), todoText);
    }

    @Test
    public void addTwoTodos() {
        // given (page todo page loads with a todo input)
        WebElement newTodoInput = driver.findElement(By.className("new-todo"));
        String todo1 = "drink water";
        String todo2 = "go to the gym";

        // when (two todos are added)
        newTodoInput.sendKeys(todo1 + "\n");
        newTodoInput.sendKeys(todo2 + "\n");

        // then (the two todos appears in the list)
        List<WebElement> todos = driver.findElements(By.cssSelector(".todo-list li"));
        Assert.assertEquals(todos.size(), 2);
        Assert.assertEquals(todos.get(0).getText(), todo2);
        Assert.assertEquals(todos.get(1).getText(), todo1);
    }

    @Test
    public void completeTodo() {
        // given (page todo page loads with two todos)
        WebElement newTodoInput = driver.findElement(By.className("new-todo"));
        String todo1 = "drink water";
        String todo2 = "go to the gym";

        // when (two todos are added)
        newTodoInput.sendKeys(todo1 + "\n");
        newTodoInput.sendKeys(todo2 + "\n");

        // when (complete the first todo)
        List<WebElement> todos = driver.findElements(By.cssSelector(".todo-list li"));
        WebElement firstCheckbox = todos.get(0).findElement(By.className("toggle"));
        firstCheckbox.click();

        // then (the first todo is completed
        Assert.assertTrue(todos.get(0).findElement(By.className("toggle")).isSelected());
        Assert.assertTrue(todos.get(1).findElement(By.className("toggle")).isSelected());
    }
}
