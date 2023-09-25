package no.hvl.dat250.rest.todos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Rest-Endpoint for todos.
 */
@RestController
public class TodoController {

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

  private HashMap<Long, Todo> todos = new HashMap<>();
  private Long idCounter = 0L;

  private Long genId() { return idCounter++; }

  @PostMapping("/todos")
  public Todo createTodo(@RequestBody Todo newTodo) {
    newTodo.setId(genId());
    todos.put(newTodo.getId(), newTodo);
    return newTodo;
  }

  @GetMapping("/todos/{id}")
  public ResponseEntity<Object> getTodo(@PathVariable Long id) {
    if (todos.containsKey(id))
      return new ResponseEntity<>(todos.get(id), HttpStatus.ACCEPTED);
    return new ResponseEntity<>(String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id), HttpStatus.NOT_FOUND);
  }

  @GetMapping("/todos")
  public List<Todo> getTodo() {
    return todos.values().stream().toList();
  }

  @PutMapping("/todos/{id}")
  public Todo updateTodo(@PathVariable Long id, @RequestBody Todo newTodo) {
    if (!todos.containsKey(id))
      return null;
    newTodo.setId(id);
    todos.put(id, newTodo);
    return newTodo;
  }

  @DeleteMapping("/todos/{id}")
  public ResponseEntity<Object> deleteTodo(@PathVariable Long id) {
    if (!todos.containsKey(id))
      return new ResponseEntity<>(String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(todos.remove(id), HttpStatus.ACCEPTED);
  }

}
