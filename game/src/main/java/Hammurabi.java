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
    int starved = 0;
    int immigrants = 5;
    boolean gameOn = true;

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        do{
            for (year = 0; year <= 10; year++) {
                printSummary();
                askHowManyAcresToBuy(price, bushelStash);
                askHowManyAcresToSell(acres);
                askHowMuchGrainToFeedPeople(bushelStash);
                askHowManyAcresToPlant(acres, people, bushelStash);
                printReport();
            }
        } while (gameOn);
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
                System.out.println("You now own " + acres + " of land\n");
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
                System.out.println("You now own " + acres + " of land\n");
                return acres;
            }
        }
    }

    public int askHowMuchGrainToFeedPeople(int bushels){
        while(true) {
            int grainToFeed = getNumber("How much grain do you wanna feed your people?? \n");
            if (grainToFeed > bushels){
                System.out.println("You only have " + bushelStash + " pick a different amount");
            } else {
                bushelStash -= grainToFeed;
                System.out.println("Thank you for feeding the people " + grainToFeed + " bushels of grain you currently have " + bushelStash + "\n");
                return grainToFeed;
            }
        }
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        while(true){
            System.out.println("Each person needs 20 bushels, each person can farm 10 acres, it takes 2 bushels of grain to farm an acre ");
            System.out.println("You have " + acres + " acres " + people + " people and " + bushelStash + " bushels\n");
            int acresToPlant = getNumber("How many acres do you wanna plant?? \n");
            int bushelsNeeded = acresToPlant * 2;
            int peopleNeeded = acresToPlant / 10;
                if (acresOwned >= acresToPlant && population >= peopleNeeded && bushels >= bushelsNeeded) {
                    bushelStash -= bushelsNeeded;
                    System.out.println("You have " + bushelStash + " bushels left\n");
                    return bushelStash;
                } else {
                    System.out.println("You either do not have enough bushels, people or acres owned to plant this much! ");
                    System.out.println("You have " + acres + " acres " + bushelStash + " bushels and " + people + " people\n");
                }
            }

        }

    public int plagueDeaths(int people) {
        int random = rand.nextInt(100) + 1;
        if (random < 15) {
            System.out.println("Half your people died");
            people = people/2;
            return people;
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
        return (int) peopleStarved;
    }

    public boolean uprising(int people, int howManyPeopleStarved) {
        double pop = (double) people * 0.45;
        if (howManyPeopleStarved > pop) {
            gameOn = false;
            System.out.println("Too many people starved you lose");
            return true;
        }
        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
            int results = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
            people += results;
            return results;
    }

    public int harvest(int acres) {
        int random = rand.nextInt(6) + 1;
        return acres * random;
    }

    public int grainEatenByRats(int bushels) {
        int random = rand.nextInt(100) + 1;
        if (random < 40) {
            double ratChance = rand.nextInt(21) + 10;
            return (int) (bushels * ratChance / 100);
        }
        return 0;
    }

    public int newCostOfLand() {
        int random = rand.nextInt(7) + 17;
        price = random;
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
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year " + starved + " people starved to death.\n" +
                "In the previous year " + immigrants + " people entered the kingdom.\n" +
                "The population is now " + people + ".\n" +
                "We harvested 3000 bushels at 3 bushels per acre.\n" +
                "Rats destroyed " + grainEatenByRats(bushelStash) + " bushels, leaving " + bushelStash + " bushels in storage.\n" +
                "The city owns " + acres + " acres of land.\n" +
                "Land is currently worth " + price + " bushels per acre.\n");
    }

    public void printReport(){

    }

    public String finalSummary(){
        return null;
    }

}
