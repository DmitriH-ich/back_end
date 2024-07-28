package app.code;

import app.staff.administration.Director;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import app.staff.administration.ProductionChief;
import app.staff.administration.SalesChief;
import app.staff.specialists.Secretary;
import app.staff.specialists.production.MachineOperator;
import app.staff.specialists.production.Storekeeper;
import app.staff.specialists.sales.Merchandiser;
import app.staff.specialists.sales.SalesManager;


public class Application {

    public static void main(String[] args) {


/*        Secretary secretary = new Secretary();


        SalesManager salesManager = new SalesManager();
        Merchandiser merchandiser = new Merchandiser();

        MachineOperator machineOperator = new MachineOperator();
        Storekeeper storekeeper = new Storekeeper();

        ProductionChief productionChief = new ProductionChief();
        productionChief.setMachineOperator(machineOperator);
        productionChief.setStorekeeper(storekeeper);

        SalesChief salesChief = new SalesChief();
        salesChief.setMerchandiser(merchandiser);
        salesChief.setSalesManager(salesManager);

        Director director = new Director();
        director.setSecretary(secretary);
        director.setProductionChief(productionChief);
        director.setSalesChief(salesChief);
        director.manageCompany();*/

        // Вариант кода с использованием Spring
        AbstractApplicationContext context = new AnnotationConfigApplicationContext("app.config");
        Director director = context.getBean(Director.class);
        director.manageCompany();


    }
}
