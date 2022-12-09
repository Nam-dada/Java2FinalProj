package com.example.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@RestController
@RequestMapping("/alibaba/fastjson/contributors")
public class contributors {
  Scanner in = new Scanner(new File("Contributors.txt"));
  ArrayList<String> a = new ArrayList<>();
  public contributors() throws FileNotFoundException {
    while (in.hasNext()) {
      a.add(in.next());
    }
  }
  @GetMapping
  public String get() {
    String js = JSON.toJSONString(a);
    return js;
  }
}
