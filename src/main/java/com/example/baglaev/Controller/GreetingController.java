package com.example.baglaev.Controller;

import com.example.baglaev.Model.User;
import com.example.baglaev.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class GreetingController {

    private User oldUser;

    @Value("${upload.path}")
    private String uploadPath;

    private Utils utils;

    @Autowired
    private UserRepo userRepo;

    /*Страница с приветствием и логином*/
    @GetMapping("/")
    public String loadGreeting(Model model) {
        return "greeting";
    }

    @PostMapping("/")
    public String login(
            @RequestParam Map<String, String> map,
            Model model
    ){
        oldUser = userRepo.findByUserName(map.get("userName"));
        if (oldUser == null || !oldUser.getPassword().equals(map.get("password"))) {
            model.addAttribute("message", "Введенные данные некорректны :(");
            oldUser = null;
        }
        else return "redirect:/main";

        return "greeting";
    }
    //---------------

    /*страница с регистрацией*/
    @GetMapping("/registration")
    public String loadRegistration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@RequestParam Map<String,String> form, Model model){

        if (userRepo.findByUserName(form.get("username")) != null){
            model.addAttribute("message", "данный пользователь уже сужествует!");
            return "registration";
        }

        utils = new Utils();
        String validate = utils.validate(form);
        if (validate != null){
            model.addAttribute("message", validate);
            return "registration";
        }

        User newUser = new User();
        newUser.setUserName(form.get("username"));
        newUser.setPassword(form.get("password"));
        newUser.setName(form.get("name"));
        newUser.setSecondName(form.get("secondName"));

        userRepo.save(newUser);

        return "redirect:/";
    }

    //---------------

    /*основная страница*/

    @GetMapping("/main")
    public String loadMain(Model model){
        oldUser.setCount(oldUser.getCount()+1); //счетчик посещений увеличился
        userRepo.save(oldUser); //сохрани мне это
        model.addAttribute("time",new Date().toString());
        model.addAttribute("user",oldUser);
        return "main";
    }

    @PostMapping("/main")
    public String updateUser(
            Model model,
            @RequestParam Map<String, String> map
            )
    {

        String userName = map.get("username");
        String name = map.get("name");
        String secondName = map.get("secondName");
        String password = map.get("password");


        if (userName!= null && userName.length() != 0){
            model.addAttribute("time",new Date().toString());
            model.addAttribute("user",oldUser);
            if (userName.length() < 5){
                model.addAttribute("message","ваш логин сильно короткий");
                return "main";
            }else {
                if (userName.equals(oldUser.getUserName())){
                    model.addAttribute("message","вы ввели одинаковые логины");

                    return "main";
                }else {
                    oldUser.setUserName(userName);
                }
            }
        }

        if (password!= null && password.length() != 0){
            if (password.length() < 6){
                model.addAttribute("message","ваш пароль сильно короткий");
                model.addAttribute("time",new Date().toString());
                model.addAttribute("user",oldUser);
                return "main";
            }else {
                if (password.equals(oldUser.getPassword())){
                    model.addAttribute("message","вы ввели одинаковые пароли");
                    model.addAttribute("time",new Date().toString());
                    model.addAttribute("user",oldUser);
                    return "main";
                }else {
                    oldUser.setPassword(password);
                }
            }
        }

        if (name != null && name.length() != 0){
            oldUser.setName(name);
        }

        if (secondName != null && secondName.length() != 0){
            oldUser.setSecondName(secondName);
        }
        userRepo.save(oldUser);
        model.addAttribute("time",new Date().toString());
        model.addAttribute("user",oldUser);

        return "main";
    }


    @PostMapping("/addphoto")
    public String addPhoto(
            @RequestParam("file")MultipartFile file,
            Model model
    ) throws IOException
    {
        if (file!= null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String fileUUID = UUID.randomUUID().toString();
            fileUUID += "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" +fileUUID));
            oldUser.setFileName(fileUUID);
            userRepo.save(oldUser);
        }
        model.addAttribute("time",new Date().toString());
        model.addAttribute("user",oldUser);

        return "main";
    }
}