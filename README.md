# Bereshit Simulation Ex0

##### Course: Intro To Space Engineering

This repository is for the first exercise in the space engineering course at ariel university.
In this readme we cover the following topics:

- Why did the breshit spacecraft failed? (see [Our Report](https://github.com/ibrahimchahine/IntroToSpaceEx0/blob/main/Ex1-Report.pdf)).
- How we implemented the simulation.
- Results from out simulation.

## How did we implement the simulation?
In this part we added the PID class to the implementation given to use by our lecturer, we use the PID class to control the vertical speed by passing the error for the vertical speed and horizontal speed, and also we control the angle of the craft. for more info check our code.

## Results from out simulation.
In this part we will list our results for the simulation.
First we address the vs and dvs, see the figure bellow:
![Graph for the vs and dvs](https://github.com/ibrahimchahine/IntroToSpaceEx0/blob/main/pics/VS_DVS.png)

We can see that in the simulation, the craft tried to adjust the vs to the desired vs.
so in the end we got to vs=1.86, and fuel=10.7.
These were our results, for more (see [Results csv](https://github.com/ibrahimchahine/IntroToSpaceEx0/blob/main/results/our_simulation.csv)) .

#### Authors
Ibrahim Chahine, Yehonatan Amosi
