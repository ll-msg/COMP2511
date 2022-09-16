1. Invisible player can pass directly through the boulders and doors without interacting with them.
2. The spider can escape from the original circling movement range when the player is invincible.
3. Each enemy can only attack one target per tick, and attack the player first.
4. When one bottle of potion lasts for the duration, using another bottle of potion will overwrite the effect of the previous potion, and the potion duration will be updated to the new potion duration.
5. It takes only one treasure to bribe a mercenary.
6. In peace mode, the player and the enemy do not fight.
7. Non-player moving entities can pass through the door opened by the player.
8. Non-player moving entities cannot move boulders, they will be blocked by them.
9. The player and the enemy may die at the same time during the battle in one tick.
10. Boulders cannot be pushed onto bombs that have been placed.
11. Boulders cannot be pushed onto non-player entites
12. When the player and the enemy on the adjacent grid (up, down, left, and right) are moving towards each other, they will switch positions after the current tick moves, and no battle will occur.
13. Battling when the player is invincible does not consume the durability of the player’s items
14. Mercenaries and spiders can pass through portals and be teleported
15. The combat detection radius of mercenaries is two cells up and down, left and right, and four diagonal cells.
    x  x  R  x  x
    x  R  R  R  x
    R  R  M  R  R
    x  R  R  R  x
    x  x  R  x  x
16. In peaceful mode, the movement behaviour of non-player entities is affected by the potion the same as in normal mode, except that no battle will occur.
17. Both potions have 10 seconds duration.
18. After the spider break the circle loop(invincinble state / teleported), after the effect ends, it will start a new circle loop at current position
19. The probability that a zombie spawn with an armour is 10%, and the probabilty that a mercenary spawn with an armour is 20%.
20. Health setting: Player - 100(hard mode is 80), Zombie - 50, Spider - 20, Mercenary - 80.
21. The format of entity id is type + number of that specific entity
22. Mercenary will move towards the player under the normal state, each tick find the shortest distance between four directions and the player.
23. Spiders will be randomly generated within three cells around the player
24. When the zombie moves, it will move in four directions with equal probability. If the target position is blocked by an object, it will not move.
25. Spiders will randomly spawn within three cells around the player.
26. Durability of the sword, armour, bow and shield is 10.
27. The armour reduces damage by 30% and the shield reduces damage by 20%.
28. Both player and enemy have attack damage 1.
29. After the mercenary becomes ally, the mercenary can attack out of the range that it can detect battle (in assumption 15).
30. The max numbers of spider is 5.
31. Hydra spawns in the same position as mercenaries
32. The health and the attack damage of Hydra is 60 and 1.0
33. The health of hydra can exceed the initialise health when it spawns new head in battle
34. Anduril can destroy spawner. Initially use normal sword to destroy, then bow, finally anduril
35. Battle will use anduril first and if there’s no anduril, use sword
36. Anduril damage is triple of sword damage(sword - 10, anduril - 30)
37. MidnightArmour and sceptre which are built with sun stone won’t have durability (can be used forever)
38. MidnightArmour can be used along with normal armour in the battle
39. MidnightArmour defence equals armour defence, attack equals sword attack
40. Allies will also be affected by the swamp
41. The spider generates every 5 ticks
42. Mercenaries spawn every 25 ticks
43. The damage the sword does to the enemy is 10
44. The damage of friendly units to the enemy = (ally health * ally attack damage) / 5
45. Anduril deals triple sword damage to Bosses, but the damage to normal enemies is the same as swords.
46. Using a scepter to control enemies will not be counted as a tick
47. After the time for using the scepter to control the enemy is over (at 10th tick), you can use it to control the same enemy again
48. Enemies bribed or controlled can no longer be interactable 
49. Midnight armor can only be used as an armor, so it cannot be used as a weapon to destroy the zombie spawner
50. SunStone can  only be consumed after using it to craft something
51. As the assassin is more powerful, the assassin’s attack damage is 2.0, which is twice the damage of the mercenary.


