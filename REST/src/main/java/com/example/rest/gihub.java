package com.example.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@RestController
@RequestMapping("/commits")
public class gihub {
  Scanner in = new Scanner(new File("commits.txt"));
  
  public gihub() throws FileNotFoundException {
  }
  
  @GetMapping
  public String get() {
    ArrayList<String> a = new ArrayList<>();
    while (in.hasNext()) {
      a.add(in.next());
    }
    String js = JSON.toJSONString(a);
    return js;
  }
}
