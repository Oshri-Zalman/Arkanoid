# 🧱 Arkanoid - Java Arcade Game

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/Architecture-OOP%20%7C%20SOLID-blue?style=for-the-badge)
![Design Patterns](https://img.shields.io/badge/Design_Pattern-Observer-success?style=for-the-badge)

A classic **Arkanoid (Brick Breaker)** 2D arcade game built entirely from scratch in Java. 
This project was developed with a strong emphasis on **Object-Oriented Programming (OOP)** principles, strict architecture, and clean code mechanics.

<img width="1571" height="1241" alt="image" src="https://github.com/user-attachments/assets/c5e489c3-8a75-4197-afc5-dda7ea55a0f6" />


## ✨ Features

* **Physics & Collisions:** Accurate ball reflections based on intersection points and regions (the paddle is divided into 5 distinct reflection zones).
* **Dynamic Game Loop:** Smooth 60 FPS rendering using custom timing mechanisms.
* **Score Tracking:** Real-time UI indicator updating on block hits and level completion.
* **Special Bonus Blocks:** Secret blocks that split the ball into multiple new balls when hit!
* **Layered Rendering:** Backgrounds, sprites, and UI overlays drawn in precise Z-order.

## 🏗️ Architecture & OOP Concepts

What makes this project special isn't just the game, but the engine behind it. The architecture heavily utilizes:

* **SOLID Principles:** 
  * Each class has a single responsibility (e.g., `BlockRemover` handles removal, `ScoreTrackingListener` handles points).
  * The system is Open for extension but Closed for modification (e.g., adding a new `BallAdder` listener without touching the `Block` class).
* **The Observer Design Pattern:** A robust event-driven system where blocks (`HitNotifiers`) notify registered listeners (`HitListeners`) upon impact.
* **Interfaces & Polymorphism:** 
  * `Sprite`: Implemented by anything that can be drawn on the screen and updated over time (Balls, Blocks, Paddle, ScoreIndicator).
  * `Collidable`: Implemented by objects that the ball can crash into.
* **Separation of Concerns:** Distinct separation between logic (Geometry, Collision math), Entities (Game objects), and Game Flow.

## 🎮 How to Play

1. **Launch the game.**
2. Use the **Left** and **Right** arrow keys to move the paddle.
3. Prevent the balls from falling past the bottom of the screen.
4. Destroy all the blocks to win the level!
5. Look out for the **Magenta Block** — hitting it spawns extra balls!

## 🚀 Getting Started

### Prerequisites
* Java JDK 8 or higher.
* `biuoop` library (used for the GUI and keyboard sensors).

### Installation & Execution
1. Clone the repository:
   ```bash
   git clone(https://github.com/Oshri-Zalman/Arkanoid.git)
