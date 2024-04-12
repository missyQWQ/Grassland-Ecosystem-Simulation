# Grassland Ecosystem Simulation
This is a grassland ecosystem simulation contains 5 types of animals (sheep, tiger, wolf, rat, snake), 2 types of plants (wheat, grass), 3 types of weather (sunny, rainy, foggy), 2 types of time (day, night), and 2 types of disease (parasitic disease, brucellosis).

## Introduction 
It's a university project from King's College London 2019/20 4CCS1PPA Programming and Applications, and please note this project is run on BuleJ.
- Supervisors: Michael Kölling, David J. Barnes
- Author: [Yichun Zhang](https://github.com/missyQWQ)
```
To start:
    Create a Simulator object.
    Then call one of:
        + simulateOneStep - for a single step.
        + simulate - and supply a number (say 10) for that many steps.
        + runLongSimulation - for a simulation of 4000 steps.
```

## Species and Factors
Animals:
- sheep
- tiger
- wolf
- rat
- snake

Plants:
- wheat
- grass

Weather:
- sunny
- rainy
- foggy

Time:
- day
- night

Disease: 
- parasitic disease
- brucellosis

## Settings
For species (animals and plants):
- Rats eat wheats.
- Sheep eat grasses.
- Snakes eat rats.
- Tigers and wolves eat sheep.

For weather:
- Tigers don’t prey in both foggy days and rainy days.
- Wolves and snakes don’t prey in foggy days.
- Grasses grow only in rainy days.
- Sheep, rats and wheats will not be influenced by weather.

For time:
- Tigers and sheep can move in the day, but don’t move at night.
- Snakes and rats don’t move in the day, but they move at night.
- Wolves, grasses and wheats will not be influenced by time.

For diseases:
- Parasitic disease infection occurs when two animals meet. It spread through animal-animal contact.
- Brucellosis affects only particular species (sheep, tiger, wolf). At first, it only spreads in sheep through sheep-sheep contact. Then, tiger and wolf will contract it from sheep if they consume the sick sheep. Finally, it spreads through tiger-tiger contact, wolf-wolf contact and sheep-sheep contact.

## Rules
1. Plants are able to grow at a given rate but can’t move. They will die if eaten by herbivores.

2. Animals can be divided into carnivores (predators) and herbivores. Carnivores eat other animals while herbivores eat plants.

3. Animals distinguish male and female individuals, they can only breed when they reach the breed age and a male and female individual with the same species meet.

4. Animals can also age and this could result in animal’s death because of old age.

5. Animals are able to eat and move. They will move towards a source of food if found and eat it. This could increase their food level which is the number of steps they can go before has to eat again. They will die of hungry if the food level reaches zero. If no food found, they will move to a free location. They will die of overcrowding if no food found as well as no more free space. In general, animals might die of old age, overcrowding, hungry, be eaten and disease.

6. Animals might get disease and this could result in death.

7. Animals getting these diseases will have a random 1-15 days latent period. During this period, the animal has a probability of infecting other animals. At the end of this period, the animal will either die or cure. The 2 diseases have different ways of spreading infection.  
