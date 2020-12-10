package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Map;

@RestController
public class EndpointsController {

    @GetMapping("/math/pi")
    public String pi() {
        return "3.141592653589793";
    }

    @GetMapping("/math/calculate")
    public String calculate(
            @RequestParam(value = "operation", defaultValue = "add") String operation,
            @RequestParam("x") String x,
            @RequestParam("y") String y) {
        if (operation.equals("add")) {
            int sum = Integer.parseInt(x) + Integer.parseInt(y);
            return x+" + "+y+" = "+sum;
        }
        if (operation.equals("multiply")) {
            int product = Integer.parseInt(x) * Integer.parseInt(y);
            return x+" * "+y+" = "+product;
        }
        if (operation.equals("subtract")) {
            int difference = Integer.parseInt(x) - Integer.parseInt(y);
            return x+" - "+y+" = "+difference;
        }
        if (operation.equals("divide")) {
            int quotient = Integer.parseInt(x) / Integer.parseInt(y);
            return x+" / "+y+" = "+quotient;
        }
        return "";
    }

    @PostMapping("/math/sum")
    public String sum(@RequestParam int[] n) {
        int sum = 0;
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < n.length; i++) {
            if (i == 0) {
                output.append(n[i]);
            } else {
                output.append(" + "+n[i]);
            }
            sum += n[i];
        }
        output.append(" = "+sum);
        return output.toString();
    }

    @RequestMapping("/math/volume/{length}/{width}/{height}")
    public String volume(
            @PathVariable String length,
            @PathVariable String width,
            @PathVariable String height) {
        int volume = Integer.parseInt(length)*Integer.parseInt(width)*Integer.parseInt(height);
        return "The volume of a "+length+"x"+width+"x"+height+" rectangle is "+volume;
    }

    @PostMapping(value = "/math/area", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String area(@RequestParam Map<String, String> body) {
        if (body.get("type").equals("circle") && body.containsKey("radius")) {
            int radius = Integer.parseInt(body.get("radius"));
            double area = Math.PI * Math.pow(radius, 2);
            DecimalFormat df = new DecimalFormat("0.#####");
            return "Area of a circle with a radius of "+radius+" is "+df.format(area);
        } else if (body.get("type").equals("rectangle") && body.containsKey("width")
                && body.containsKey("height")) {
            int width = Integer.parseInt(body.get("width"));
            int height = Integer.parseInt(body.get("height"));
            int area = width * height;
            return "Area of a "+width+"x"+height+" rectangle is "+area;
        } else {
            return "Invalid";
        }
    }
}