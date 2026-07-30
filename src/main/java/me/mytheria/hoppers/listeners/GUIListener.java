if (event.getSlot() == 11) {


    int next =
            data.getSpeedLevel() + 1;


    int max =
            plugin.getConfig()
                    .getInt(
                    "settings.max-speed-level"
            );


    if (next > max) {

        player.sendMessage(
                color("&cMaximum speed level!")
        );

        return;
    }


    data.setSpeedLevel(next);


    plugin.getDataManager()
            .save();


    player.sendMessage(
            color(
            "&dMytheria Hoppers &8» &aSpeed upgraded!"
            )
    );

}



if (event.getSlot() == 15) {


    int next =
            data.getRangeLevel() + 1;


    int max =
            plugin.getConfig()
                    .getInt(
                    "settings.max-range-level"
            );


    if (next > max) {

        player.sendMessage(
                color("&cMaximum range level!")
        );

        return;
    }


    data.setRangeLevel(next);


    plugin.getDataManager()
            .save();


    player.sendMessage(
            color(
            "&dMytheria Hoppers &8» &aRange upgraded!"
            )
    );

}
