package myapplicationtest;

import myapplication.model.Role;
import myapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RestTemplateTest {



    public void getUserByIdDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class, 1);
        User user = responseEntity.getBody();
        System.out.println("Id:" + user.getId() +
                            ", Name:" + user.getName() +
                            ", Age:"+ user.getAge() +
                            ", Email:" + user.getEmail() +
                            ", Password:" + user.getPassword());
    }
    public void getAllUsersDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/list";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User[].class);
        User[] users = responseEntity.getBody();
        for(User user : users) {
            System.out.println("Id:" + user.getId() +
                    ", Name:" + user.getName() +
                    ", Age:"+ user.getAge() +
                    ", Email:" + user.getEmail() +
                    ", Password:" + user.getPassword() +
                    ", Role:" + user.getRole());
        }
    }
    public void addUserDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/add";

        User objUser = new User();
        Role objRole = new Role();
        objRole.setName("ROLE_SUPERADMIN");
        objUser.setName("Dinara");
        objUser.setAge(25);
        objUser.setEmail("dinara@mail.ru");
        objUser.setPassword("superadmin");
        objUser.setRole(objRole);

        HttpEntity<User> requestEntity = new HttpEntity<User>(objUser, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());
    }
    public void updateUserDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/update/{id}";

        User objUser = new User();
        objUser.setId(11);
        objUser.setName("Nasty");
        objUser.setAge(33);
        objUser.setEmail("nasty@mail.ru");
        objUser.setPassword("user");
        objUser.setRole(new Role(2, "ROLE_USER"));

        HttpEntity<User> requestEntity = new HttpEntity<User>(objUser, headers);
        restTemplate.put(url, requestEntity);
    }
    public void deleteUserDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/admin/delete/{id}";
        HttpEntity<User> requestEntity = new HttpEntity<User>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);
    }

    public static void main(String args[]) {
        RestTemplateTest template = new RestTemplateTest();
        //template.getUserByIdDemo();
        //template.addUserDemo();
        template.getAllUsersDemo();
        //template.updateUserDemo();
        //template.deleteUserDemo();
    }
}
