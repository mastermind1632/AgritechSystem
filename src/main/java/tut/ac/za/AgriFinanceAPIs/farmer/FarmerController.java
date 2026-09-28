package tut.ac.za.AgriFinanceAPIs.farmer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tut.ac.za.AgriFinanceAPIs.farmer.dto.FarmerResponse;
import tut.ac.za.AgriFinanceAPIs.farmer.dto.LoginRequest;
import tut.ac.za.AgriFinanceAPIs.farmer.dto.RegisterRequest;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public FarmerResponse register(@RequestBody RegisterRequest request) {
        return new FarmerResponse(farmerService.register(
                request.getName(), request.getLocation(), request.getContact(), request.getPassword()));
    }

    @PostMapping("/login")
    public FarmerResponse login(@RequestBody LoginRequest request) {
        return new FarmerResponse(farmerService.login(request.getContact(), request.getPassword()));
    }

    @GetMapping("/{id}")
    public FarmerResponse getFarmer(@PathVariable String id) {
        return new FarmerResponse(farmerService.getById(id));
    }
}
