package myapplication.controller;

import myapplication.model.Role;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest_role")
public class RoleController {

    Logger logger = Logger.getLogger(RestController.class.getName());

    @GetMapping("/list")
    public List<Role> getAllRoles() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/role/list";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Role[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Role[].class);
        Role[] roles = responseEntity.getBody();
        List<Role> roleList = Arrays.asList(roles);
        return roleList;
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/role/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Role> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Role.class, id);
        Role role = responseEntity.getBody();
        return role;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoleById(@PathVariable("id") Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/role/delete/{id}";
        restTemplate.delete(url, id);
        logger.info("Role with id " + id + " has deleted!");
    }

    @PostMapping("/add")
    public void addRole(@RequestBody Role role) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/role/add";
        restTemplate.postForObject(url, role, Role.class);
        logger.info("New User has added!");
    }

    @PutMapping("/update/{id}")
    public void updateRole(@RequestBody Role role, @PathVariable("id") Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/role/update/{id}";
        HttpEntity<Role> requestEntity = new HttpEntity<>(role, headers);
        restTemplate.put(url, requestEntity, id);
        logger.info("Role with id " + id + " has updated!");
    }


}
