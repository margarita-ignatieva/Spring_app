package controllers;

import dto.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inject")
    public void inject() {
        log.info("Adding 4 users to DB");
        User shinoda = new User("mike@mail.com", "123er");
        User chester = new User("ches@mail.com", "123ch");
        User joe = new User("hann@mail.com", "123hann");
        User feniks = new User("fn@mail.com", "123fn");
        userService.add(shinoda);
        userService.add(chester);
        userService.add(joe);
        userService.add(feniks);
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        log.info("Getting userDto by id" + userId);
        return copyUserToUserDto(userService.get(userId));
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        log.info("Getting all userDtos");
        return userService.listUsers().stream()
        .map(x -> copyUserToUserDto(x))
        .collect(Collectors.toList());
    }

    private UserResponseDto copyUserToUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        return userResponseDto;
    }
}
