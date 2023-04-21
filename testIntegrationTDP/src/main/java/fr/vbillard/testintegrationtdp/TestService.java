package fr.vbillard.testintegrationtdp;

import org.springframework.stereotype.Component;

import fr.vbillard.tissusdeprincesseboot.service.TissuService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TestService {

  Factory factory;
  TissuService tissuService;


  public void test1(){
    factory.setBdd();

    System.out.println("tissus enregistrés: "+tissuService.getAll().size());

  }

}
