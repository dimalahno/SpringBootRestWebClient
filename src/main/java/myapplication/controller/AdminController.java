package myapplication.controller;

import myapplication.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


@RestController()
@RequestMapping("/rest_admin")
public class AdminController {

    Logger logger = Logger.getLogger(AdminController.class.getName());

    @GetMapping("/list")
    public List<User> getAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/list";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User[].class);
        User[] users = responseEntity.getBody();
        List<User> userList = Arrays.asList(users);
        return userList;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class, id);
        User user = responseEntity.getBody();
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/delete/{id}";
        restTemplate.delete(url, id);
        logger.info("User with id " + id + " has deleted!");
    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/add";
        restTemplate.postForObject(url, user, User.class);
        logger.info("New User has added!");

    }

    @PutMapping("/update/{id}")
    public void updateUser(@RequestBody User user, @PathVariable("id") Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/update/{id}";
        HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
        restTemplate.put(url, requestEntity, id);
        logger.info("User with id " + id + " has updated!");
    }
}
