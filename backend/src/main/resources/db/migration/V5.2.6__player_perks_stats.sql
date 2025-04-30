-- Création de la table perk
CREATE TABLE perk
(
    id            BIGSERIAL PRIMARY KEY,
    code          VARCHAR(255) NOT NULL UNIQUE,
    names         JSONB        NOT NULL,
    descriptions  JSONB        NOT NULL,
    image_path    VARCHAR(512) NOT NULL,
    display_order INTEGER      NOT NULL    DEFAULT 0,
    visible       BOOLEAN      NOT NULL    DEFAULT true,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table perk_instance
CREATE TABLE perk_instance
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT  NOT NULL REFERENCES character (id) ON DELETE CASCADE,
    perk_id      BIGINT  NOT NULL REFERENCES perk (id) ON DELETE CASCADE,
    value        INTEGER NOT NULL,
    is_active    BOOLEAN NOT NULL         DEFAULT false,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_character_perk UNIQUE (character_id, perk_id)
);


INSERT INTO perk (code, names, descriptions, image_path, display_order, visible)
VALUES ('awareness', '{
  "en": "Awareness",
  "fr": "Intuition"
}', '{
  "en": "You are more likely to notice details about people. Awareness will give more detailed information about them when you perform an Examine.",
  "fr": "Grâce à l\"Intuition, tu recevras des informations détaillées sur toutes les créatures que tu examineras. Tu peux ainsi connaître leur nombre exact de points d\"impact et obtenir des informations sur leurs armes."
}', '/assets/perk/Awareness.webp', 0, true),
       ('bonus_hth_attacks', '{
         "en": "Bonus HtH Attacks",
         "fr": "Bonus attaques en Rixe"
       }', '{
         "en": "You have learned the secret arts of the East, or you just punch faster. In any case, your Hand-to-Hand attacks cost 1 AP less to perform.",
         "fr": "Soit tu as appris les secrets des arts martiaux, soit tu cognes plus vite que tout le monde. Dans les deux cas, chaque attaque en Rixe (CàC ou Mêlée) te coûte 1 PA de moins."
       }', '/assets/perk/Bonus_HtH_Attacks.webp', 1, true),
       ('bonus_hth_damage', '{
         "en": "Bonus HtH Damage",
         "fr": "Bonus dégâts en Rixe"
       }', '{
         "en": "Experience in unarmed combat has given you the edge when it comes to damage. You cause +2 points of damage with hand-to-hand and melee attacks for each level of this Perk.",
         "fr": "Ton expérience en Rixe fait de toi un adversaire redoutable. A chaque niveau de cette aptitude, tes attaques au Corps à Corps ou en Mêlée infligent +2 points de dégâts à ton adversaire."
       }', '/assets/perk/Bonus_HtH_Damage.webp', 2, true),
       ('bonus_move', '{
         "en": "Bonus Move",
         "fr": "Bonus déplacement"
       }', '{
         "en": "For each level of Bonus Move, you get 2 free APs each turn that can only be used for movement. In other words, you can move 2 free hexes each turn for each level of this Perk.",
         "fr": "A chaque niveau de cette aptitude, tu reçois 2 PA gratuits à chaque tour, uniquement utilisables pour te déplacer. Autrement dit, tu peux te déplacer de deux cases gratuitement pour chaque niveau de cette aptitude."
       }', '/assets/perk/Bonus_Move.webp', 3, true),
       ('bonus_ranged_damage', '{
         "en": "Bonus Ranged Damage",
         "fr": "Bonus dégâts à distance"
       }', '{
         "en": "Your training in firearms and other ranged weapons has made you more deadly in ranged combat. For each level of this Perk, you do +2 points of damage with ranged weapons.",
         "fr": "Ton expérience des armes à feu et des autres armes à distance te rend redoutable. A chaque niveau de cette aptitude, tu infliges +2 points de dégâts avec des armes à distance."
       }', '/assets/perk/Bonus_Ranged_Damage.webp', 4, true),
       ('bonus_rate_of_fire', '{
         "en": "Bonus Rate of Fire",
         "fr": "Bonus cadence de tir"
       }', '{
         "en": "This Perk allows you to pull the trigger a little more faster, and still remain as accurate as before. Each ranged weapon attack costs 1 AP less to perform.",
         "fr": "Cette aptitude te permet de tirer un peu plus vite tout en conservant la même précision. Chaque attaque avec une arme à distance te coûte 1 PA de moins."
       }', '/assets/perk/Bonus_Rate_of_Fire.webp', 5, true),
       ('earlier_sequence', '{
         "en": "Earlier Sequence",
         "fr": "Meilleure position"
       }', '{
         "en": "You are more likely to move before your opponents in combat, since your Sequence is +2 for each level of this Perk.",
         "fr": "Tu augmentes tes chances de précéder tes adversaires au combat, car à chaque niveau de cette aptitude, ta position (Séquence) augmente de +2."
       }', '/assets/perk/Earlier_Sequence.webp', 6, true),
       ('faster_healing', '{
         "en": "Faster Healing",
         "fr": "Guérison plus rapide"
       }', '{
         "en": "With each level of this Perk, you will get a +1 bonus to your Healing Rate. Thus you heal faster.",
         "fr": "A chaque niveau de cette aptitude, ta vitesse de guérison augmente de +1, ce qui te permet de guérir plus vite."
       }', '/assets/perk/Faster_Healing.webp', 7, true),
       ('more_criticals', '{
         "en": "More Criticals",
         "fr": "Plus de coups critiques"
       }', '{
         "en": "You are more likely to cause Critical Hits in combat if you have this Perk. Each level of More Criticals will get you a +5% chance to cause a critical hit. This is a good thing.",
         "fr": "Avec cette aptitude, tu augmentes tes chances d\"infliger des coups critiques au combat. Chaque niveau de cette aptitude augmente tes chances de +5%. C\"est un avantage non négligeable."
       }', '/assets/perk/More_Criticals.webp', 8, true),
       ('night_vision', '{
         "en": "Night Vision",
         "fr": "Vision nocturne"
       }', '{
         "en": "With the Night Vision Perk, you can see in the dark better. Each level of this Perk will reduce the overall darkness level by 10%.",
         "fr": "Grâce à la vision nocturne, tu vois mieux dans l\"obscurité. Chaque niveau de cette aptitude réduit l\"obscurité générale de 10%."
       }', '/assets/perk/Night_Vision.webp', 9, true),
       ('presence', '{
         "en": "Presence",
         "fr": "Présence"
       }', '{
         "en": "You command attention by just walking into a room. The initial reaction of another person is improved by 10% for each level of this Perk.",
         "fr": "Tu attires l\"attention simplement en traversant une pièce. La réaction initiale des personnes qui t\"entourent est améliorée de 10% à chaque niveau de cette aptitude."
       }', '/assets/perk/Presence.webp', 10, true),
       ('rad_resistance', '{
         "en": "Rad Resistance",
         "fr": "Résistance aux rad."
       }', '{
         "en": "You are better able to avoid radiation, and the bad effects radiation causes. Each level of this Perk will improve your Radiation Resistance by 10%.",
         "fr": "Tu échappes plus facilement aux radiations et à leurs effets nocifs. A chaque niveau de cette aptitude, ta résistance aux radiations augmente de 15%."
       }', '/assets/perk/Rad_Resistance.webp', 11, true),
       ('toughness', '{
         "en": "Toughness",
         "fr": "Résistance"
       }', '{
         "en": "When you are tough, you take less damage. Each level of this Perk will add +10% to your general damage resistance.",
         "fr": "Lorsque ta résistance est élevée, tu subis moins de dégâts. A chaque niveau de cette aptitude, ta résistance générale aux dégâts augmente de +10%."
       }', '/assets/perk/Toughness.webp', 12, true),
       ('strong_back', '{
         "en": "Strong Back",
         "fr": "Dos résistant"
       }', '{
         "en": "AKA Mule. You can carry an additional 50 lbs. of equipment for each level of this Perk.",
         "fr": "Alias Mulet. A chaque niveau de cette aptitude, tu peux transporter 25 Kg (50 lbs) de matériel en plus."
       }', '/assets/perk/Strong_Back.webp', 13, true),
       ('sharpshooter', '{
         "en": "Sharpshooter",
         "fr": "Tireur d\"élite"
       }', '{
         "en": "The talent of hitting things at longer distances. You get a +2 bonus, for each level of this Perk, to Perception for the purposes of determining range modifiers. It\"s easier than ever to kill at long range.",
         "fr": "Tu as la capacité de toucher ta cible sur de plus longues distances. A chaque niveau de cette aptitude, tu reçois un bonus de +2 en Perception dans le but de déterminer la portée. Valable AUSSI avec une Perception de 9 ou 10."
       }', '/assets/perk/Sharpshooter.webp', 14, true),
       ('silent_running', '{
         "en": "Silent Running",
         "fr": "Course silencieuse"
       }', '{
         "en": "With this Perk, you now have the ability to move quickly and still remain quiet. You can Sneak, and run at the same time. Without this Perk, you would automatically stop Sneaking if you ran.",
         "fr": "Avec cette aptitude, tu peux désormais courir tout en restant silencieux. Tu peux utiliser ta compétence Esquive et courir en même temps. Sans cette aptitude, tu ne pourrais pas faire les deux."
       }', '/assets/perk/Silent_Running.webp', 15, true),
       ('survivalist', '{
         "en": "Survivalist",
         "fr": "Survivant"
       }', '{
         "en": "You are a master of the outdoors. This Perk confers the ability to survive in hostile environments. You get a +20% bonus to Outdoorsman for survival purposes, for each level of this Perk.",
         "fr": "Cette aptitude te permet de survivre dans un environnement hostile. A chaque niveau de cette aptitude, tu reçois un bonus de +20% en Vie en plein air, et +20% additionnels pour les situations de survie en milieu sauvage."
       }', '/assets/perk/Survivalist.webp', 16, true),
       ('master_trader', '{
         "en": "Master Trader",
         "fr": "Maître marchand"
       }', '{
         "en": "You have mastered one aspect of bartering - the ability to buy goods far cheaper than a normal person. With this Perk, you get a 25% discount when purchasing items from a store or another trader.",
         "fr": "Maître dans l\"art de négocier, tu achètes des marchandises à des prix plus intéressants que quiconque. Tu bénéficies de 25% de remise sur les achats effectués dans un magasin ou auprès d\"un marchand."
       }', '/assets/perk/Master_Trader.webp', 17, true),
       ('educated', '{
         "en": "Educated",
         "fr": "Erudit"
       }', '{
         "en": "Each level of Educated will add +2 skill points when you gain a new experience level. This Perk works best when purchased early in your adventure.",
         "fr": "A chaque niveau de cette aptitude, tu reçois +5 points de compétence par niveau d’expérience supplémentaire acquis. Pour tirer le meilleur parti de cette aptitude, tâche de l\"acheter au début de ton aventure."
       }', '/assets/perk/Educated.webp', 18, true),
       ('healer', '{
         "en": "Healer",
         "fr": "Guérisseur"
       }', '{
         "en": "The healing of bodies comes easier to you with this Perk. Each level will add 2-5 more hit points healed when using the First Aid or Doctor skills.",
         "fr": "Cette aptitude te permet de guérir plus efficacement les blessures. A chaque niveau de cette aptitude, tu reçois 2 à 5 points d\"impact guéris supplémentaires lorsque tu utilises les compétences Secourisme ou Médecin."
       }', '/assets/perk/Healer.webp', 19, true);

INSERT INTO perk (code, names, descriptions, image_path, display_order, visible)
VALUES ('fortune_finder', '{
  "en": "Fortune Finder",
  "fr": "Découvreur de trésor"
}', '{
  "en": "You have the talent of finding money. You will find additional money in random encounters in the desert.",
  "fr": "Tu as le talent de trouver de l\"argent, ou bien tu es juste chanceux. Tu trouveras plus d\"argent au cours des rencontres aléatoires."
}', '/assets/perk/Fortune_Finder.webp', 20, true),
       ('better_criticals', '{
         "en": "Better Criticals",
         "fr": "Meilleurs coups critiques"
       }', '{
         "en": "The critical hits you cause in combat are more devastating. You gain a 20% bonus on the critical hit table. This does not affect the chance to cause a critical hit.",
         "fr": "Les coups critiques que tu infliges au combat sont plus redoutables. Bonus de 20% sur le tableau des coups critiques. Tu crées donc plus de dégâts. Ceci n\"affecte pas tes chances d\"infliger des coups critiques."
       }', '/assets/perk/Better_Criticals.webp', 21, true),
       ('empathy', '{
         "en": "Empathy",
         "fr": "Empathie"
       }', '{
         "en": "You have studied other human beings, giving you the inside knowledge of their emotional reaction to you.",
         "fr": "Tu as étudié les autres êtres humains, ce qui te permet d\"évaluer leurs réactions émotionnelles à ton égard. Tu peux voir le niveau de réaction de la personne avec qui tu parles lors d\"une conversation approfondie."
       }', '/assets/perk/Empathy.webp', 22, true),
       ('slayer', '{
         "en": "Slayer",
         "fr": "Tueur"
       }', '{
         "en": "The Slayer walks the earth! In Hand-to-Hand combat, all of your hits are upgraded to critical hits - causing destruction and mayhem.",
         "fr": "La Mort est parmi nous ! Lors des combats en Rixe, tous tes coups sont transformés en coups critiques. Tu sèmes alors la terreur et la destruction sur ton passage."
       }', '/assets/perk/Slayer.webp', 23, true),
       ('sniper', '{
         "en": "Sniper",
         "fr": "Tireur embusqué"
       }', '{
         "en": "You have mastered the firearm as a source of pain. Any successful hit in combat with a ranged weapon will be upgraded to a critical hit if you also make a Luck roll.",
         "fr": "Tu maîtrises le maniement des armes à feu pour semer la terreur. Avec cette aptitude, chaque tir réussi avec une arme à distance se transforme en coup critique, à condition de réussir un test de chance."
       }', '/assets/perk/Sniper.webp', 24, true),
       ('silent_death', '{
         "en": "Silent Death",
         "fr": "Mort silencieuse"
       }', '{
         "en": "While Sneaking, if you hit a critter in the back, you will cause double damage using a HtH attack.",
         "fr": "Avec cette aptitude, la compétence Esquive te permet de lancer une attaque en Rixe (CàC ou Mêlée) qui, si tu attaques ton adversaire par derrière, lui infligera deux fois plus de dégâts. C\"est ça l\"aptitude Mort Silencieuse !"
       }', '/assets/perk/Silent_Death.webp', 25, true),
       ('action_boy', '{
         "en": "Action Boy",
         "fr": "Homme d\"action"
       }', '{
         "en": "Each level of Action Boy (insert Girl if you wish) gives you +1 Action Point per turn.",
         "fr": "A chaque niveau de l\"aptitude Homme (ou Femme) d\"action, tu reçois +1 PA supplémentaire à dépenser à chaque tour de combat. Ces PA peuvent être utilisés pour n\"importe quelle tâche."
       }', '/assets/perk/Action_Boy.webp', 26, true),
       ('mental_block', '{
         "en": "Mental Block",
         "fr": "Bloc mental"
       }', '{
         "en": "Mental Block is the ability to tune out any outside mental interference.",
         "fr": "Le Bloc mental est l\"aptitude à déjouer toute interférence mentale extérieure. Ce talent doit t\"avoir été enseigné par un gourou de passage ou à la fin d\"une soirée dans un bar."
       }', '/assets/perk/Mental_Block.webp', 27, true),
       ('lifegiver', '{
         "en": "Lifegiver",
         "fr": "Donneur de vie"
       }', '{
         "en": "With each level of this Perk, you gain an additional 4 Hit Points.",
         "fr": "A chaque niveau de cette aptitude, ton maximum de points d\"impact augmente de 4. Tu reçois aussi un bonus de 4 points pour chaque niveau d\"expérience supplémentaire."
       }', '/assets/perk/Lifegiver.webp', 28, true),
       ('dodger', '{
         "en": "Dodger",
         "fr": "Tire-au-flanc"
       }', '{
         "en": "Each level of this Perk adds +5 to your Armor Class, in addition to worn armor bonuses.",
         "fr": "Lorsque tu possèdes cette aptitude, tes chances d\"être touché(e) au combat diminuent. A chaque niveau de cette aptitude, tu reçois un bonus de Classe d\"armure de +5, en plus du bonus de l\"armure que tu portes."
       }', '/assets/perk/Dodger.webp', 29, true),
       ('snakeater', '{
         "en": "Snakeater",
         "fr": "Mangeur de serpents"
       }', '{
         "en": "You have gained an immunity to poison, resulting in a +25% to your Poison Resistance.",
         "fr": "Miam-miam ! Ca a le goût de poulet. Tu as acquis une immunisation contre le poison, ce qui augmente ta résistance au poison de +25%."
       }', '/assets/perk/Snakeater.webp', 30, true),
       ('mr_fixit', '{
         "en": "Mr. Fixit",
         "fr": "M. Réparetout"
       }', '{
         "en": "This Perk gives you a +20% bonus to the Repair and Science skills.",
         "fr": "Avec cette aptitude, tu reçois un bonus de +20% aux compétences Réparation et Science."
       }', '/assets/perk/Mr._Fixit.webp', 31, true),
       ('medic', '{
         "en": "Medic",
         "fr": "Médecin"
       }', '{
         "en": "The Medic Perk gives you a +20% bonus to the First Aid and Doctor skills.",
         "fr": "Avec l\"aptitude Médecin, tu reçois un bonus de +20% aux compétences Secourisme et Médecin."
       }', '/assets/perk/Medic.webp', 32, true),
       ('master_thief', '{
         "en": "Master Thief",
         "fr": "Maître Voleur"
       }', '{
         "en": "A Master Thief has a +10% bonus to Sneak, Lockpick, Steal, and Traps skills.",
         "fr": "Avec l\"aptitude Maître Voleur, tu reçois un bonus de +10% aux compétences Esquive, Passe-partout, Voleur, et Pièges."
       }', '/assets/perk/Master_Thief.webp', 33, true),
       ('speaker', '{
         "en": "Speaker",
         "fr": "Orateur"
       }', '{
         "en": "Being a Speaker gives a +20% bonus to Speech and Barter.",
         "fr": "Avec l\"aptitude Orateur, tu reçois un bonus de +20% aux compétences Discours et Troc."
       }', '/assets/perk/Speaker.webp', 34, true),
       ('heave_ho', '{
         "en": "Heave Ho!",
         "fr": "Oh Hisse !"
       }', '{
         "en": "Each level gives +2 Strength when calculating range with thrown weapons.",
         "fr": "A chaque niveau de cette aptitude, tu reçois +2 en Force (jusqu\"à 10) pour déterminer la portée avec des armes de jet uniquement."
       }', '/assets/perk/Heave_Ho!.webp', 35, true),
       ('friendly_foe', '{
         "en": "Friendly Foe",
         "fr": "Ennemi amical"
       }', '{
         "en": "Team members appear green instead of red in combat.",
         "fr": "Les membres de ton équipe n\"apparaissent plus en rouge mais en vert durant les combats."
       }', '/assets/perk/Friendly_Foe.webp', 36, true),
       ('pickpocket', '{
         "en": "Pickpocket",
         "fr": "Pickpocket"
       }', '{
         "en": "You are much more adept at Stealing than the normal crook.",
         "fr": "Avec cette aptitude, tu peux voler mieux que n\"importe quel escroc."
       }', '/assets/perk/Pickpocket.webp', 37, true),
       ('ghost', '{
         "en": "Ghost",
         "fr": "Fantôme"
       }', '{
         "en": "You move like a Ghost in the dark. +20% Sneak at night.",
         "fr": "Lorsque le soleil se couche, ou lorsque tu te trouves dans un lieu mal éclairé, cette aptitude te permet de te déplacer tel un fantôme."
       }', '/assets/perk/Ghost.webp', 38, true),
       ('cult_of_personality', '{
         "en": "Cult of Personality",
         "fr": "Culte de la personnalité"
       }', '{
         "en": "Your reputation is always positive to people.",
         "fr": "Ta réputation est toujours positive. Même une mauvaise réputation n’a pas d’effet sur les gens bienveillants."
       }', '/assets/perk/Cult_of_Personality.webp', 39, true);

INSERT INTO perk (code, names, descriptions, image_path, display_order, visible)
VALUES ('scrounger', '{
  "en": "Scrounger",
  "fr": "Chapardeur"
}', '{
  "en": "You can find more ammo than the normal post-holocaust survivor. This Perk will double the amount of ammo found in random encounters.",
  "fr": "Tu peux trouver plus de munitions que n\"importe quel survivant de la Grande Guerre. Cette aptitude double la quantité de munitions que tu trouveras au cours des rencontres aléatoires."
}', '/assets/perk/Scrounger.webp', 40, true),
       ('explorer', '{
         "en": "Explorer",
         "fr": "Explorateur"
       }', '{
         "en": "The mark of the Explorer is to search out new and interesting locations. With this Perk, you have a greater chance of finding special places or peoples.",
         "fr": "L\"objectif de l\"explorateur est de trouver de nouveaux sites intéressants. Avec cette aptitude, tu as plus de chances de découvrir des lieux ou des gens sortant de l\"ordinaire."
       }', '/assets/perk/Explorer.webp', 41, true),
       ('flower_child', '{
         "en": "Flower Child",
         "fr": "Enfant des Fleurs"
       }', '{
         "en": "With this Perk, you are much less likely to be addicted to chems (50% less likely), and you suffer half the withdrawal time of a normal person.",
         "fr": "Avec cette aptitude, tu as deux fois moins de chances de devenir accro aux stimulants et leurs effets secondaires durent deux fois moins longtemps que sur une personne normale."
       }', '/assets/perk/Flower_Child.webp', 42, true),
       ('pathfinder', '{
         "en": "Pathfinder",
         "fr": "Pionnier"
       }', '{
         "en": "The Pathfinder is better able to find the shortest route. With this Perk, your travel time on the World Map is reduced by 25% for each level.",
         "fr": "Un Pionnier est plus apte que quiconque à trouver le chemin le plus court. A chaque niveau de cette aptitude, le temps qu\"il te faut pour voyager sur la Carte du Monde est réduit de 25%."
       }', '/assets/perk/Pathfinder.webp', 43, true),
       ('animal_friend', '{
         "en": "Animal Friend",
         "fr": "Ami des animaux"
       }', '{
         "en": "Animals will not attack one of their friends, unless the animal is threatened or attacked first.",
         "fr": "Les animaux n\"attaquent jamais leurs amis à moins d\"être menacés ou attaqués. Quant à la définition du mot animal dans ce monde, le débat est ouvert."
       }', '/assets/perk/Animal_Friend.webp', 44, true),
       ('scout', '{
         "en": "Scout",
         "fr": "Eclaireur"
       }', '{
         "en": "You have improved your ability to see distant locations, increasing the size of explorations on the World Map by one square in each direction.",
         "fr": "Tu as amélioré ta capacité repérer des endroits éloignés, ce qui augmente la taille de tes explorations sur la carte du monde d\"un carré dans chaque direction."
       }', '/assets/perk/Scout.webp', 45, true),
       ('mysterious_stranger', '{
         "en": "Mysterious Stranger",
         "fr": "Etranger mystérieux"
       }', '{
         "en": "You have gained the attention of a Mysterious Stranger, who will appear to help you from time to time. If your ally dies, they won\"t be replaced.",
         "fr": "Avec cette aptitude, tu as attiré l\"attention d\"un étranger mystérieux, qui apparaîtra de temps en temps pour t\"aider. Si cet allié meurt au combat, ne t\"attends pas à ce qu\"on te le remplace."
       }', '/assets/perk/Mysterious_Stranger.webp', 46, true),
       ('ranger', '{
         "en": "Ranger",
         "fr": "Commando"
       }', '{
         "en": "You are better able to avoid unwanted attention while traveling through the wasteland. Your chance of a hostile random encounter is lowered by each level.",
         "fr": "Tu es en mesure de moins attirer l\"attention lorsque tu voyages dans le désert. A chaque niveau de cette aptitude, tes chances de faire de mauvaises rencontres diminuent."
       }', '/assets/perk/Ranger.webp', 47, true),
       ('quick_pockets', '{
         "en": "Quick Pockets",
         "fr": "Poches"
       }', '{
         "en": "You have learned to better store your equipment. The AP cost to access Inventory in combat is reduced by one.",
         "fr": "Tu as appris à mieux ranger ton équipement. A chaque niveau de cette aptitude, le coût en PA pour accéder à ton inventaire pendant un combat est réduit de 1."
       }', '/assets/perk/Quick_Pockets.webp', 48, true),
       ('smooth_talker', '{
         "en": "Smooth Talker",
         "fr": "Beau parleur"
       }', '{
         "en": "A Smooth Talker increases dialogue options. Each level gives +1 Intelligence for dialogue only.",
         "fr": "Les beaux parleurs s\"expriment bien, sans forcément comprendre de quoi ils parlent. A chaque niveau de cette aptitude, tu reçois 1 point d\"Intelligence, à des fins de dialogue uniquement."
       }', '/assets/perk/Smooth_Talker.webp', 49, true),
       ('swift_learner', '{
         "en": "Swift Learner",
         "fr": "Elève vif"
       }', '{
         "en": "You are indeed a Swift Learner with this Perk, gaining +5% experience bonus. Best purchased early.",
         "fr": "Tu apprends plus vite. A chaque niveau de cette aptitude, tu reçois un bonus de +5% lorsque tu gagnes de l\"expérience. A choisir de préférence au cours des premiers niveaux."
       }', '/assets/perk/Swift_Learner.webp', 50, true),
       ('tag', '{
         "en": "Tag!",
         "fr": "Atout !"
       }', '{
         "en": "Pick an additional Tag Skill, gaining +20% instantly, and 2% per skill point spent.",
         "fr": "Compte tenu de l\"amélioration de tes compétences, tu peux sélectionner un nouvel atout personnel. Tu recevras immédiatement +20%, puis 2% par point de compétence utilisé pour cet atout."
       }', '/assets/perk/Tag.webp', 51, true),
       ('mutate', '{
         "en": "Mutate!",
         "fr": "Mutation !"
       }', '{
         "en": "The radiation of the wasteland has changed you! One of your Traits has mutated into something else...",
         "fr": "Les radiations du désert ont eu de l\"influence sur toi ! Une de tes qualités s\"est transformée en quelque chose d\"autre..."
       }', '/assets/perk/Mutate.webp', 52, true);
