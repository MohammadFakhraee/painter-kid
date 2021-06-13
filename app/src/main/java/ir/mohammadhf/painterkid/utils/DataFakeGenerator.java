package ir.mohammadhf.painterkid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.ColorPalette;
import ir.mohammadhf.painterkid.data.model.PaintModel;
import ir.mohammadhf.painterkid.data.model.PaintPackage;

public class DataFakeGenerator {
    public static final int PACKAGE_FOOD_ID = 3;
    public static final int PACKAGE_PRINCESS_ID = 4;
    public static final int PACKAGE_VEHICLE_ID = 5;
    public static final int PACKAGE_ANIMATION_CHARACTER_ID = 6;

    public static List<PaintPackage> getPackageModels() {
        List<PaintPackage> paintPackages = new ArrayList<>();
        paintPackages.add(new PaintPackage(0, "animals", R.drawable.gif_main_avatar_animal,
                R.drawable.gif_main_title_animals, false));
        paintPackages.add(new PaintPackage(1, "fruits", R.drawable.gif_main_avatar_fruit,
                R.drawable.gif_main_title_fruit, false));
        paintPackages.add(new PaintPackage(2, "musics", R.drawable.gif_main_avatar_music,
                R.drawable.gif_main_title_music, false));

        paintPackages.add(new PaintPackage(PACKAGE_FOOD_ID, "foods", R.drawable.gif_main_avatar_food,
                R.drawable.gif_main_title_food, true));
        paintPackages.add(new PaintPackage(PACKAGE_VEHICLE_ID, "vehicles", R.drawable.gif_main_avatar_vehicle,
                R.drawable.gif_main_title_vehicle, true));
        paintPackages.add(new PaintPackage(PACKAGE_ANIMATION_CHARACTER_ID, "cartoons", R.drawable.gif_main_avatar_cartoon,
                R.drawable.gif_main_title_cartoon, true));
        paintPackages.add(new PaintPackage(PACKAGE_PRINCESS_ID, "princesses", R.drawable.gif_main_avatar_princess,
                R.drawable.gif_main_title_princess, true));
        return paintPackages;
    }

    public static List<PaintModel> getRawPaintModels(Context context, String folderName) {
        List<PaintModel> models = new ArrayList<>();

        switch (folderName) {
            case "musics":
                setMusicModels(models, context);
                break;
            case "fruits":
                setFruitModels(models, context);
                break;
            case "princesses":
                setPrincessesModels(models, context);
                break;
            case "foods":
                setFoodsModels(models, context);
                break;
            case "cartoons":
                setCartoonsModels(models, context);
                break;
            case "vehicles":
                setVehiclesModels(models, context);
                break;
            default:
                setAnimalModels(models, context);
                break;
        }
        return models;
    }

    private static void setAnimalModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "animals";

        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_elephant),
                "Elephant" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_lion),
                "Lion" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_cat),
                "Cat" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_bear),
                "Bear" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_rabbit),
                "Rabbit" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_turtle),
                "Turtle" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_dog),
                "Dog" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_dolphin),
                "Dolphin" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_bird),
                "Bird" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_dinosaur),
                "Dinosaur" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_fish),
                "Fish" + ".jpg", category,
                context));

        models.add(createModel(11,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_pig),
                "Pig" + ".jpg", category,
                context));

        models.add(createModel(12,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_animals_tiger),
                "Tiger" + ".jpg", category,
                context));
    }

    private static void setFruitModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "fruits";
        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_banana),
                "Banana" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_apple),
                "Apple" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_cucumber),
                "Cucumber" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_watermelon),
                "Watermelon" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_cherry),
                "Cherry" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_carrot),
                "Carrot" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_corn),
                "Corn" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_strawberry),
                "Strawberry" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_grape),
                "Grape" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_kiwi),
                "Kiwi" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_onion),
                "Onion" + ".jpg", category,
                context));

        models.add(createModel(11,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_pear),
                "Pear" + ".jpg", category,
                context));

        models.add(createModel(12,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_pineapple),
                "Pineapple" + ".jpg", category,
                context));

        models.add(createModel(13,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_fruits_coconut),
                "Coconut" + ".jpg", category,
                context));
    }

    private static void setMusicModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "musics";
        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_guitar),
                "Guitar" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_percussion),
                "Percussion" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_microphone),
                "Microphone" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_horn),
                "Horn" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_xylophone),
                "Xylophone" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_flute),
                "Flute" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_piano),
                "Piano" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_violin),
                "Violin" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_speaker),
                "Speaker" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_saxophone),
                "Saxophone" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_organ),
                "Organ" + ".jpg", category,
                context));

        models.add(createModel(11,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_bells),
                "Bells" + ".jpg", category,
                context));
    }

    private static void setFoodsModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "foods";

        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_pizza),
                "Pizza" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_cake),
                "Cake" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_cookie),
                "Cookie" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_friedchicken),
                "FriedChicken" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_icecream),
                "IceCream" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_cupcake),
                "CupCake" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_sandwich),
                "Sandwich" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_frenchfries),
                "FrenchFries" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_icecreamcone),
                "IceCreamCone" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_foods_toast),
                "Toast" + ".jpg", category,
                context));
    }

    private static void setVehiclesModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "vehicles";

        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_racingcar),
                "RacingCar" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_bicycle),
                "Bicycle" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_spaceship),
                "SpaceShip" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_car),
                "Car" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_airplane),
                "Airplane" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_boat),
                "Boat" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_motorcycle),
                "MotorCycle" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_taxi),
                "Taxi" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_truck),
                "Truck" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_policecar),
                "PoliceCar" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_vehicles_ambulance),
                "Ambulance" + ".jpg", category,
                context));
    }

    private static void setPrincessesModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "princesses";

        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_cinderella),
                "Cinderella" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_snowwhite),
                "SnowWhite" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_rapunzel),
                "Rapunzel" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_elsa),
                "Elsa" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_ariel),
                "Ariel" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_aurora),
                "Aurora" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_belle),
                "Belle" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_jasmine),
                "Jasmine" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_merida),
                "Merida" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_mulan),
                "Mulan" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_princesses_tiana),
                "Tiana" + ".jpg", category,
                context));
    }

    private static void setCartoonsModels(@NotNull List<PaintModel> models, @NotNull Context context) {
        String category = "cartoons";

        models.add(createModel(0,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_unicorn),
                "Unicorn" + ".jpg", category,
                context));

        models.add(createModel(1,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_unicorn2),
                "Unicorn2" + ".jpg", category,
                context));

        models.add(createModel(2,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_minion),
                "Minion" + ".jpg", category,
                context));

        models.add(createModel(3,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_spongebob),
                "SpongeBob" + ".jpg", category,
                context));

        models.add(createModel(4,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_patrick),
                "Patrick" + ".jpg", category,
                context));

        models.add(createModel(5,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_pinkpanther),
                "PinkPanther" + ".jpg", category,
                context));

        models.add(createModel(6,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_kitty),
                "Kitty" + ".jpg", category,
                context));

        models.add(createModel(7,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_bugsbunny),
                "BugsBunny" + ".jpg", category,
                context));

        models.add(createModel(8,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_mickeymouse),
                "MickeyMouse" + ".jpg", category,
                context));

        models.add(createModel(9,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_tom),
                "Tom" + ".jpg", category,
                context));

        models.add(createModel(10,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_jerry),
                "Jerry" + ".jpg", category,
                context));

        models.add(createModel(11,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_pooh),
                "Pooh" + ".jpg", category,
                context));

        models.add(createModel(12,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_cartoons_garfield),
                "Garfield" + ".jpg", category,
                context));
    }

    private static PaintModel createModel(int position, Bitmap bitmap, String paintName, String category, Context context) {
        PaintModel paintModel = new PaintModel();
        paintModel.setBitmap(bitmap);
        paintModel.setNameWithFormat(paintName);
        paintModel.setPaintCategory(category);

        switch (position % 5) {
            case 0:
                paintModel.setPaintHolderId(R.drawable.template_paint_blue_dark);
                paintModel.setPaintButtonBgId(R.drawable.template_name_blue_dark);
                paintModel.setNameColor(ContextCompat.getColor(context, R.color.white));
                break;
            case 1:
                paintModel.setPaintHolderId(R.drawable.template_paint_red_dark);
                paintModel.setPaintButtonBgId(R.drawable.template_name_red_dark);
                paintModel.setNameColor(ContextCompat.getColor(context, R.color.white));
                break;
            case 2:
                paintModel.setPaintHolderId(R.drawable.template_paint_green_dark);
                paintModel.setPaintButtonBgId(R.drawable.template_name_green_dark);
                paintModel.setNameColor(ContextCompat.getColor(context, R.color.white));
                break;
            case 3:
                paintModel.setPaintHolderId(R.drawable.template_paint_orange_dark);
                paintModel.setPaintButtonBgId(R.drawable.template_name_orange_dark);
                paintModel.setNameColor(ContextCompat.getColor(context, R.color.white));
                break;
            case 4:
                paintModel.setPaintHolderId(R.drawable.template_paint_purple);
                paintModel.setPaintButtonBgId(R.drawable.template_name_purple);
                paintModel.setNameColor(ContextCompat.getColor(context, R.color.white));
                break;
        }

        return paintModel;
    }

    public static List<String> getRawPaintNames(@NotNull String folderName) {
        List<String> strings = new ArrayList<>();
        switch (folderName) {
            case "musics":
                getMusicNames(strings);
                break;
            case "fruits":
                getFruitNames(strings);
                break;
            case "princesses":
                getPrincessNames(strings);
                break;
            case "foods":
                getFoodsName(strings);
                break;
            case "cartoons":
                getCartoonsName(strings);
                break;
            case "vehicles":
                getVehiclesName(strings);
                break;
            default:
                getAnimalNames(strings);
        }
        return strings;
    }

    private static void getVehiclesName(List<String> strings) {
        strings.add("RacingCar" + ".jpg");
        strings.add("Bicycle" + ".jpg");
        strings.add("SpaceShip" + ".jpg");
        strings.add("Car" + ".jpg");
        strings.add("Airplane" + ".jpg");
        strings.add("Boat" + ".jpg");
        strings.add("MotorCycle" + ".jpg");
        strings.add("Taxi" + ".jpg");
        strings.add("Truck" + ".jpg");
        strings.add("PoliceCar" + ".jpg");
        strings.add("Ambulance" + ".jpg");
    }

    private static void getFruitNames(List<String> strings) {
        strings.add("Banana" + ".jpg");

        strings.add("Apple" + ".jpg");

        strings.add("Cucumber" + ".jpg");

        strings.add("Watermelon" + ".jpg");

        strings.add("Cherry" + ".jpg");

        strings.add("Carrot" + ".jpg");

        strings.add("Corn" + ".jpg");

        strings.add("Strawberry" + ".jpg");

        strings.add("Grape" + ".jpg");

        strings.add("Kiwi" + ".jpg");

        strings.add("Onion" + ".jpg");

        strings.add("Pear" + ".jpg");

        strings.add("Pineapple" + ".jpg");

        strings.add("Coconut" + ".jpg");
    }

    private static void getMusicNames(List<String> strings) {
        strings.add("Guitar" + ".jpg");

        strings.add("Percussion" + ".jpg");

        strings.add("Microphone" + ".jpg");

        strings.add("Horn" + ".jpg");

        strings.add("Xylophone" + ".jpg");

        strings.add("Flute" + ".jpg");

        strings.add("Piano" + ".jpg");

        strings.add("Violin" + ".jpg");

        strings.add("Speaker" + ".jpg");

        strings.add("Saxophone" + ".jpg");

        strings.add("Organ" + ".jpg");

        strings.add("Bells" + ".jpg");
    }

    private static void getAnimalNames(List<String> strings) {
        strings.add("Elephant" + ".jpg");

        strings.add("Lion" + ".jpg");

        strings.add("Cat" + ".jpg");

        strings.add("Bear" + ".jpg");

        strings.add("Rabbit" + ".jpg");

        strings.add("Turtle" + ".jpg");

        strings.add("Dog" + ".jpg");

        strings.add("Dolphin" + ".jpg");

        strings.add("Bird" + ".jpg");

        strings.add("Dinosaur" + ".jpg");

        strings.add("Fish" + ".jpg");

        strings.add("Pig" + ".jpg");

        strings.add("Tiger" + ".jpg");
    }

    private static void getFoodsName(List<String> strings) {
        strings.add("Pizza" + ".jpg");
        strings.add("Cake" + ".jpg");
        strings.add("Cookie" + ".jpg");
        strings.add("FriedChicken" + ".jpg");
        strings.add("IceCream" + ".jpg");
        strings.add("CupCake" + ".jpg");
        strings.add("Sandwich" + ".jpg");
        strings.add("FrenchFries" + ".jpg");
        strings.add("IceCreamCone" + ".jpg");
        strings.add("Toast" + ".jpg");
    }

    private static void getCartoonsName(List<String> strings) {
        strings.add("Unicorn" + ".jpg");
        strings.add("Unicorn2" + ".jpg");
        strings.add("Minion" + ".jpg");
        strings.add("SpongeBob" + ".jpg");
        strings.add("Patrick" + ".jpg");
        strings.add("PinkPanther" + ".jpg");
        strings.add("Kitty" + ".jpg");
        strings.add("BugsBunny" + ".jpg");
        strings.add("MickeyMouse" + ".jpg");
        strings.add("Tom" + ".jpg");
        strings.add("Jerry" + ".jpg");
        strings.add("Pooh" + ".jpg");
        strings.add("Garfield" + ".jpg");
    }

    private static void getPrincessNames(List<String> strings) {
        strings.add("Cinderella" + ".jpg");
        strings.add("SnowWhite" + ".jpg");
        strings.add("Rapunzel" + ".jpg");
        strings.add("Elsa" + ".jpg");
        strings.add("Ariel" + ".jpg");
        strings.add("Aurora" + ".jpg");
        strings.add("Belle" + ".jpg");
        strings.add("Jasmine" + ".jpg");
        strings.add("Merida" + ".jpg");
        strings.add("Mulan" + ".jpg");
        strings.add("Tiana" + ".jpg");
    }

    public static List<String> getTalkerStrings() {
        List<String> musics = new ArrayList<>();
        musics.add("Rina");
        musics.add("Ivy");

        return musics;
    }

    public static class Palette {
        public static Single<List<ColorPalette>> getColorPack() {
            return Single.create(emitter -> {
                if (!emitter.isDisposed()) {
                    List<ColorPalette> colors = new ArrayList<>();
                    colors.add(new ColorPalette(R.color.palette_blue_light, R.color.palette_blue_light));
                    colors.add(new ColorPalette(R.color.palette_blue_dark, R.color.palette_blue_dark));
                    colors.add(new ColorPalette(R.color.palette_green_light, R.color.palette_green_light));
                    colors.add(new ColorPalette(R.color.palette_green_dark, R.color.palette_green_dark));
                    colors.add(new ColorPalette(R.color.palette_yellow_light, R.color.palette_yellow_light));
                    colors.add(new ColorPalette(R.color.palette_yellow_dark, R.color.palette_yellow_dark));
                    colors.add(new ColorPalette(R.color.palette_orange_dark, R.color.palette_orange_dark));
                    colors.add(new ColorPalette(R.color.palette_orange_light, R.color.palette_orange_light));
                    colors.add(new ColorPalette(R.color.palette_red_light, R.color.palette_red_light));
                    colors.add(new ColorPalette(R.color.palette_red_dark, R.color.palette_red_dark));
                    colors.add(new ColorPalette(R.color.palette_pink_light, R.color.palette_pink_light));
                    colors.add(new ColorPalette(R.color.palette_pink_dark, R.color.palette_pink_dark));
                    colors.add(new ColorPalette(R.color.palette_purple_deep_light, R.color.palette_purple_deep_light));
                    colors.add(new ColorPalette(R.color.palette_purple_deep_dark, R.color.palette_purple_deep_dark));
                    colors.add(new ColorPalette(R.color.palette_brown_light, R.color.palette_brown_light));
                    colors.add(new ColorPalette(R.color.palette_brown_dark, R.color.palette_brown_dark));
                    colors.add(new ColorPalette(R.color.palette_white, R.color.palette_white));
                    colors.add(new ColorPalette(R.color.palette_creme, R.color.palette_creme));
                    colors.add(new ColorPalette(R.color.palette_gray, R.color.palette_gray));
                    colors.add(new ColorPalette(R.color.palette_black, R.color.palette_black));

                    emitter.onSuccess(colors);
                }
            });
        }

        public static Single<List<ColorPalette>> getPatternPack() {
            return Single.create(emitter -> {
                List<ColorPalette> colorPalettes = new ArrayList<>();
                colorPalettes.add(new ColorPalette(R.drawable.tile_heart, R.drawable.tile_heart));
                colorPalettes.add(new ColorPalette(R.drawable.tile_heart2, R.drawable.tile_heart2));
                colorPalettes.add(new ColorPalette(R.drawable.tile_heart3, R.drawable.tile_heart3));
                colorPalettes.add(new ColorPalette(R.drawable.tile_night, R.drawable.tile_night));
                colorPalettes.add(new ColorPalette(R.drawable.tile_star, R.drawable.tile_star));

                emitter.onSuccess(colorPalettes);
            });
        }
    }
}
