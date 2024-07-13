import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {

    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    int people = 100;
    int bushelStash = 2800;
    int acres = 1000;
    int price = 19;
    int year = 0;
    int totalDeaths;
    int deathsByStarvation;
    int immigrants = 5;
    int yield;
    int harvestAmount;
    int grainDestroyedByRats;
    int acresToPlant;
    int plagueDeaths;
    int percentDied;
    boolean gameOn = true;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        while(gameOn) {
            do {
                printSummary();
                askHowManyAcresToBuy(price, bushelStash);
                askHowManyAcresToSell(acres);
                int grainToFeed = askHowMuchGrainToFeedPeople(bushelStash);
                this.acresToPlant = askHowManyAcresToPlant(acres, people, bushelStash);
                this.plagueDeaths = plagueDeaths(people);
                this.deathsByStarvation = starvationDeaths(people, grainToFeed);
                uprising(people, deathsByStarvation);
                immigrants = (deathsByStarvation == 0) ? immigrants(people, acres, bushelStash) : 0;
                this.harvestAmount = harvest(acresToPlant);
                this.grainDestroyedByRats = grainEatenByRats(bushelStash);
                newCostOfLand();
                printReport();
                totalDeaths += plagueDeaths;
                totalDeaths += deathsByStarvation;
                year++;
                if (year == 11) {
                    finalSummary();
                }
            } while (year <= 10);
        }
    }

    public int askHowManyAcresToBuy(int price, int bushels){
        while(true) {
            int acresBought = getNumber("How many acres do you wish to buy this year?? \n");
            if (bushels <= price * acresBought){
                System.out.println("You only have " + bushels + " please try a different amount");
            } else {
                bushelStash -= price * acresBought;
                acres += acresBought;
                System.out.println("You now have " + bushelStash + " bushels left");
                System.out.println("You now own " + acres + " acres of land\n");
                return acres;
            }
        }
    }

    public int askHowManyAcresToSell(int acresOwned){
        while(true) {
            int acresSold = getNumber("How many acres do you wish to sell this year?? \n");
            if (acres <= acresSold) {
                System.out.println("You only have " + acresOwned + " available please try a different amount");
            } else {
                acres -= acresSold;
                bushelStash += price * acresSold;
                System.out.println("You now have " + bushelStash + " bushels left");
                System.out.println("You now own " + acres + " acres of land\n");
                return acres;
            }
        }
    }

    public int askHowMuchGrainToFeedPeople(int bushels){
        while(true) {
            System.out.println("Each person needs 20 bushels to survive! You currently have " + bushelStash + " bushels and " + people + " people.");
            int grainToFeed = getNumber("How much grain do you wanna feed your people?? \n");
            if (grainToFeed > bushels){
                System.out.println("You only have " + bushelStash + " pick a different amount");
            } else {
                bushelStash -= grainToFeed;
                System.out.println("\nThank you for feeding the people " + grainToFeed + " bushels of grain you now have " + bushelStash + " bushels left\n");
                return grainToFeed;
            }
        }
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        while(true){
            System.out.println("Each person can farm 10 acres and consume 2 bushels per farming acre");
            System.out.println("You have " + acres + " acres " + people + " people and " + bushelStash + " bushels currently\n");
            int acresToPlant = getNumber("How many acres do you wanna plant?? \n");
            int bushelsNeeded = acresToPlant * 2;
            int peopleNeeded = acresToPlant / 10;
                if (acresOwned >= acresToPlant && population >= peopleNeeded && bushels >= bushelsNeeded) {
                    bushelStash -= bushelsNeeded;
                    System.out.println("You now have " + bushelStash + " bushels left\n");
                    return acresToPlant;
                } else {
                    System.out.println("You either do not have enough bushels, people or acres owned to plant this much! ");
                    System.out.println("You have " + acres + " acres " + bushelStash + " bushels and " + people + " people\n");
                }
            }

        }

    public int plagueDeaths(int people) {
        if (rand.nextInt(100) + 1 <= 15) {
            System.out.println("!!!!!!!!!! Half your people died from a plague !!!!!!!!!!!!!!\n");
            this.people = people/2;
            return people/2;
        }
        return 0;
    }

    public int starvationDeaths(int people, int bushelsToFeed) {
        int grainsNeeded = people * 20;
        if (bushelsToFeed >= grainsNeeded){
            bushelStash -= grainsNeeded;
            return 0;
        }
        int bushelShortage = grainsNeeded - bushelsToFeed;
        double peopleStarved = Math.ceil((double) bushelShortage / 20);
        this.people -= (int) peopleStarved;
        deathsByStarvation += (int) peopleStarved;
        return (int) peopleStarved;
    }

    public boolean uprising(int people, int howManyPeopleStarved) {
        double pop = (double) people * 0.45;
        if (howManyPeopleStarved > pop) {
            gameOn = false;
            System.out.println("Too many people starved\n The people have now revolted\n You Lose.\n");
            return true;
        }
        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
            int results = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
            immigrants = results;
            people += results;
            return results;
    }

    public int harvest(int acres) {
        this.yield = rand.nextInt(6) + 1;
        this.bushelStash += acres * yield;
        return acres * yield;
    }

    public int grainEatenByRats(int bushels) {
        int random = rand.nextInt(100) + 1;
        if (random < 40) {
            double ratChance = rand.nextInt(21) + 10;
            this.bushelStash -= (int) (bushels * ratChance / 100);
            return (int) (bushels * ratChance / 100);
        }
        return 0;
    }

    public int newCostOfLand() {
        price = rand.nextInt(7) + 17;
        return price;
    }

    public int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    public void printSummary(){
        System.out.println("O great Hammurabi!\n" +
                "You are in year " + year + " of your 10 year rule.\n" +
                "In the previous year " + deathsByStarvation + " people starved to death.\n" +
                "In the previous year " + immigrants + " people entered the kingdom.\n" +
                "The population is now " + people + ".\n" +
                "We harvested " + harvestAmount + " bushels at " + yield + " bushels per acre.\n" +
                "Rats destroyed " + grainDestroyedByRats + " bushels, leaving " + bushelStash + " bushels in storage.\n" +
                "The city owns " + acres + " acres of land.\n" +
                "Land is currently worth " + price + " bushels per acre.\n");
    }

    public void printReport(){

    }

    public String finalSummary(){
        String finalSum = "In your 10 year term in office " + percentDied + " percent of the population starved\n" +
                " A total of " + totalDeaths + " people died\n" + "You started with 10 acres per capita and ended with " +
                acres/people + " acres per capita";
        gameOn = false;
        return finalSum;
    }

}
