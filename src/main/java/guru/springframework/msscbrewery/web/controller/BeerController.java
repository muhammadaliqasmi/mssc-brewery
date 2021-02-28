package guru.springframework.msscbrewery.web.controller;

import com.sun.net.httpserver.Headers;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody BeerDto beerDto) {
        final BeerDto saveDto = beerService.saveNewBeer(beerDto);
        final Headers header = new Headers();
        //todo - add hostname to URL
        header.add("location", "/api/v1/beer/" + saveDto.getId().toString());
        return new ResponseEntity(header, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handlePut(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        beerDto.setId(beerId);
        beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeer(beerId);
    }

}
