-- Townsfolk
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(1, 'Washerwoman', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Townsfolk.', 'https://wiki.bloodontheclocktower.com/Washerwoman'),
(2, 'Librarian', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Outsider, or that zero are in play.', 'https://wiki.bloodontheclocktower.com/Librarian'),
(3, 'Investigator', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Minion.', 'https://wiki.bloodontheclocktower.com/Investigator'),
(4, 'Chef', 'Townsfolk', TRUE, 'You start knowing how many pairs of evil assignments there are.', 'https://wiki.bloodontheclocktower.com/Chef'),
(5, 'Empath', 'Townsfolk', TRUE, 'Each night, you learn how many of your 2 alive neighbors are evil.', 'https://wiki.bloodontheclocktower.com/Empath'),
(6, 'Fortune Teller', 'Townsfolk', TRUE, 'Each night, choose 2 assignments: you learn if either is a Demon. There is a good player that always registers as a Demon to you.', 'https://wiki.bloodontheclocktower.com/Fortune_Teller'),
(7, 'Undertaker', 'Townsfolk', TRUE, 'Each night*, you learn which character died by execution today.', 'https://wiki.bloodontheclocktower.com/Undertaker'),
(8, 'Monk', 'Townsfolk', TRUE, 'Each night*, choose a player (not yourself): they are safe from the Demon tonight.', 'https://wiki.bloodontheclocktower.com/Monk'),
(9, 'Ravenkeeper', 'Townsfolk', TRUE, 'If you die at night, you are woken to choose a player: you learn their character.', 'https://wiki.bloodontheclocktower.com/Ravenkeeper'),
(10, 'Virgin', 'Townsfolk', TRUE, 'The first time you are nominated, if the nominator is a Townsfolk, they are executed immediately.', 'https://wiki.bloodontheclocktower.com/Virgin'),
(11, 'Slayer', 'Townsfolk', TRUE, 'Once per game, during the day, publicly choose a player: if they are the Demon, they die.', 'https://wiki.bloodontheclocktower.com/Slayer'),
(12, 'Soldier', 'Townsfolk', TRUE, 'You are safe from the Demon.', 'https://wiki.bloodontheclocktower.com/Soldier'),
(13, 'Mayor', 'Townsfolk', TRUE, 'If only 3 assignments live & no execution occurs, your team wins. If you die at night, another player might die instead.', 'https://wiki.bloodontheclocktower.com/Mayor');

-- Outsiders
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(14, 'Butler', 'Outsider', TRUE, 'Each night, choose a player (not yourself): tomorrow, you may only vote if they are voting too.', 'https://wiki.bloodontheclocktower.com/Butler'),
(15, 'Drunk', 'Outsider', TRUE, 'You do not know you are the Drunk. You think you are a Townsfolk character, but you are not.', 'https://wiki.bloodontheclocktower.com/Drunk'),
(16, 'Recluse', 'Outsider', TRUE, 'You might register as evil & as a Minion or Demon, even if dead.', 'https://wiki.bloodontheclocktower.com/Recluse'),
(17, 'Saint', 'Outsider', TRUE, 'If you die by execution, your team loses.', 'https://wiki.bloodontheclocktower.com/Saint');

-- Minions
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(18, 'Poisoner', 'Minion', FALSE, 'Each night, choose a player: they are poisoned tonight and tomorrow day.', 'https://wiki.bloodontheclocktower.com/Poisoner'),
(19, 'Spy', 'Minion', FALSE, 'You start knowing which characters are in play & where. You might register as good & as a Townsfolk.', 'https://wiki.bloodontheclocktower.com/Spy'),
(20, 'Scarlet Woman', 'Minion', FALSE, 'If there are 5 or more assignments alive & the Demon dies, you become the Demon.', 'https://wiki.bloodontheclocktower.com/Scarlet_Woman'),
(21, 'Baron', 'Minion', FALSE, 'There are 2 extra Outsiders in play.', 'https://wiki.bloodontheclocktower.com/Baron');

-- Demon
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(22, 'Imp', 'Demon', FALSE, 'Each night*, choose a player: they die. If you kill yourself this way, a Minion becomes the Imp.', 'https://wiki.bloodontheclocktower.com/Imp');

----------------------------------------------------------------------------------------- Bad Moon Rising
-- Townsfolk
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(23, 'Grandmother', 'Townsfolk', TRUE, 'You start knowing a good player & their character. If the Demon kills them, you die too.', 'https://wiki.bloodontheclocktower.com/Grandmother'),
(24, 'Sailor', 'Townsfolk', TRUE, 'Each night, choose a player: either you or they are drunk until dusk. You cannot die at night.', 'https://wiki.bloodontheclocktower.com/Sailor'),
(25, 'Chambermaid', 'Townsfolk', TRUE, 'Each night, choose 2 alive assignments (not yourself): you learn how many woke tonight due to their ability.', 'https://wiki.bloodontheclocktower.com/Chambermaid'),
(26, 'Exorcist', 'Townsfolk', TRUE, 'Each night*, choose a player: the Demon, if chosen, cannot kill tonight.', 'https://wiki.bloodontheclocktower.com/Exorcist'),
(27, 'Innkeeper', 'Townsfolk', TRUE, 'Each night*, choose 2 assignments (not yourself): they cannot die tonight, but one is drunk until dusk.', 'https://wiki.bloodontheclocktower.com/Innkeeper'),
(28, 'Gambler', 'Townsfolk', TRUE, 'Each night*, choose a player & guess their character: if you guess wrong, you die.', 'https://wiki.bloodontheclocktower.com/Gambler'),
(29, 'Gossip', 'Townsfolk', TRUE, 'Each day, you may make a public statement: tonight, if it is true, a player dies.', 'https://wiki.bloodontheclocktower.com/Gossip'),
(30, 'Courtier', 'Townsfolk', TRUE, 'Once per game, at night, choose a character: it is drunk for 3 nights & days.', 'https://wiki.bloodontheclocktower.com/Courtier'),
(31, 'Professor', 'Townsfolk', TRUE, 'Once per game, at night, choose a dead player (not yourself): if they are a Townsfolk, they are resurrected.', 'https://wiki.bloodontheclocktower.com/Professor'),
(32, 'Minstrel', 'Townsfolk', TRUE, 'When a Minion dies by execution, all other assignments (except Travellers) are drunk until dusk tomorrow.', 'https://wiki.bloodontheclocktower.com/Minstrel'),
(33, 'Tea Lady', 'Townsfolk', TRUE, 'If both your alive neighbors are good, they cannot die.', 'https://wiki.bloodontheclocktower.com/Tea_Lady'),
(34, 'Pacifist', 'Townsfolk', TRUE, 'Executed good assignments might not die.', 'https://wiki.bloodontheclocktower.com/Pacifist'),
(35, 'Fool', 'Townsfolk', TRUE, 'The first time you die, you don’t.', 'https://wiki.bloodontheclocktower.com/Fool');

-- Outsiders
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(36, 'Goon', 'Outsider', TRUE, 'Each night, the 1st player to choose you with their ability is drunk until dusk. You become their alignment.', 'https://wiki.bloodontheclocktower.com/Goon'),
(37, 'Lunatic', 'Outsider', TRUE, 'You think you are the Demon, but you are not. The Demon knows who you are & who you choose at night.', 'https://wiki.bloodontheclocktower.com/Lunatic'),
(38, 'Tinker', 'Outsider', TRUE, 'You might die at any time.', 'https://wiki.bloodontheclocktower.com/Tinker'),
(39, 'Moonchild', 'Outsider', TRUE, 'When you learn that you died, publicly choose 1 alive player. Tonight, if it was a good player, they die.', 'https://wiki.bloodontheclocktower.com/Moonchild');

-- Minions
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(40, 'Godfather', 'Minion', FALSE, 'You start knowing which Outsiders are in play. If 1 died today, choose a player tonight: they die. [-1 or +1 Outsider]', 'https://wiki.bloodontheclocktower.com/Godfather'),
(41, 'Devil''s Advocate', 'Minion', FALSE, 'Each night, choose a living player (different to last night): if executed tomorrow, they don''t die.', 'https://wiki.bloodontheclocktower.com/Devil%27s_Advocate'),
(42, 'Assassin', 'Minion', FALSE, 'Once per game, at night*, choose a player: they die, even if for some reason they could not.', 'https://wiki.bloodontheclocktower.com/Assassin'),
(43, 'Mastermind', 'Minion', FALSE, 'If the Demon dies by execution (ending the game), play for 1 more day. If a player is then executed, their team loses.', 'https://wiki.bloodontheclocktower.com/Mastermind');

-- Demons
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(44, 'Zombuul', 'Demon', FALSE, 'Each night*, if no-one died today, choose a player: they die. The 1st time you die, you live but register as dead', 'https://wiki.bloodontheclocktower.com/Zombuul'),
(45, 'Pukka', 'Demon', FALSE, 'Each night, choose a player: they are poisoned. The previously poisoned player dies then becomes healthy.', 'https://wiki.bloodontheclocktower.com/Pukka'),
(46, 'Shabaloth', 'Demon', FALSE, 'Each night*, choose 2 assignments: they die. A dead player you chose last night might be regurgitated.', 'https://wiki.bloodontheclocktower.com/Shabaloth'),
(47, 'Po', 'Demon', FALSE, 'Each night*, you may choose a player: they die. If your last choice was no-one, choose 3 assignments tonight.', 'https://wiki.bloodontheclocktower.com/Po');

---------------------------------------------------------------------------------- Sects & Violets

-- Townsfolk
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(48, 'Clockmaker', 'Townsfolk', TRUE, 'You start knowing how many steps from the Demon to their nearest Minion.', 'https://wiki.bloodontheclocktower.com/Clockmaker'),
(49, 'Dreamer', 'Townsfolk', TRUE, 'Each night*, choose a player (not yourself): you learn 1 good character & 1 evil character, 1 of which is correct.', 'https://wiki.bloodontheclocktower.com/Dreamer'),
(50, 'Snake Charmer', 'Townsfolk', TRUE, 'Each night*, choose a player: a chosen Demon swaps characters & alignments with you & is then poisoned.', 'https://wiki.bloodontheclocktower.com/Snake_Charmer'),
(51, 'Mathematician', 'Townsfolk', TRUE, 'Each night*, you learn how many assignments’ abilities worked abnormally tonight due to another character’s ability.', 'https://wiki.bloodontheclocktower.com/Mathematician'),
(52, 'Flowergirl', 'Townsfolk', TRUE, 'Each night*, you learn if the Demon voted today.', 'https://wiki.bloodontheclocktower.com/Flowergirl'),
(53, 'Town Crier', 'Townsfolk', TRUE, 'Each night*, you learn if a Minion nominated today.', 'https://wiki.bloodontheclocktower.com/Town_Crier'),
(54, 'Oracle', 'Townsfolk', TRUE, 'Each night*, you learn how many dead assignments are evil.', 'https://wiki.bloodontheclocktower.com/Oracle'),
(55, 'Savant', 'Townsfolk', TRUE, 'Each day, you may visit the Storyteller to learn two things: one true, one false.', 'https://wiki.bloodontheclocktower.com/Savant'),
(56, 'Seamstress', 'Townsfolk', TRUE, 'Once per game, at night*, choose two assignments: you learn if they are the same alignment.', 'https://wiki.bloodontheclocktower.com/Seamstress'),
(57, 'Philosopher', 'Townsfolk', TRUE, 'Once per game, at night*, choose a good character: gain that ability. If it is in play, its player is drunk.', 'https://wiki.bloodontheclocktower.com/Philosopher'),
(58, 'Artist', 'Townsfolk', TRUE, 'Once per game, during the day, privately ask the Storyteller any yes/no question.', 'https://wiki.bloodontheclocktower.com/Artist'),
(59, 'Juggler', 'Townsfolk', TRUE, 'On your first day, publicly guess up to five assignments’ characters. That night, you learn how many you got correct.', 'https://wiki.bloodontheclocktower.com/Juggler'),
(60, 'Sage', 'Townsfolk', TRUE, 'If the Demon kills you, you learn which player it is.', 'https://wiki.bloodontheclocktower.com/Sage');

-- Outsiders
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(61, 'Mutant', 'Outsider', TRUE, 'If you are mad about being an Outsider, you might be executed.', 'https://wiki.bloodontheclocktower.com/Mutant'),
(62, 'Sweetheart', 'Outsider', TRUE, 'When you die, 1 player is drunk from now on.', 'https://wiki.bloodontheclocktower.com/Sweetheart'),
(63, 'Barber', 'Outsider', TRUE, 'If you die, the Demon may choose 2 assignments (not themselves): those assignments swap characters.', 'https://wiki.bloodontheclocktower.com/Barber'),
(64, 'Klutz', 'Outsider', TRUE, 'If you die, publicly choose 1 alive player: if they are evil, your team loses.', 'https://wiki.bloodontheclocktower.com/Klutz');

-- Minions
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(65, 'Evil Twin', 'Minion', FALSE, 'You & a good player know each other. If the good player is executed, evil wins.', 'https://wiki.bloodontheclocktower.com/Evil_Twin'),
(66, 'Witch', 'Minion', FALSE, 'Each night*, choose a player (not yourself): if they nominate tomorrow, they die.', 'https://wiki.bloodontheclocktower.com/Witch'),
(67, 'Cerenovus', 'Minion', FALSE, 'Each night*, choose a player & a good character: they are mad about being this character tomorrow, or might be executed.', 'https://wiki.bloodontheclocktower.com/Cerenovus'),
(68, 'Pit-Hag', 'Minion', FALSE, 'Each night*, choose a player & a character they become (if not in play).', 'https://wiki.bloodontheclocktower.com/Pit-Hag');

-- Demons
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(69, 'Fang Gu', 'Demon', FALSE, 'Each night*, choose a player: they die. The first Outsider this kills becomes the Fang Gu & you die instead.', 'https://wiki.bloodontheclocktower.com/Fang_Gu'),
(70, 'Vigormortis', 'Demon', FALSE, 'Each night*, choose a player: they die. Minions you kill keep their ability & are not considered dead.', 'https://wiki.bloodontheclocktower.com/Vigormortis'),
(71, 'No Dashii', 'Demon', FALSE, 'Each night*, choose a player: they die. Your 2 neighbors are poisoned.', 'https://wiki.bloodontheclocktower.com/No_Dashii'),
(72, 'Vortox', 'Demon', FALSE, 'Each night*, choose a player: they die. All other assignments’ information is false.', 'https://wiki.bloodontheclocktower.com/Vortox');

-- Experimental

-- Townsfolk
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(73, 'Acrobat', 'Townsfolk', TRUE, 'If both your alive neighbors are evil, you die.', 'https://wiki.bloodontheclocktower.com/Acrobat'),
(74, 'Alchemist', 'Townsfolk', TRUE, 'You have the ability of a specific Minion, but you are good.', 'https://wiki.bloodontheclocktower.com/Alchemist'),
(75, 'Alsaahir', 'Townsfolk', TRUE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Alsaahir'),
(76, 'Amnesiac', 'Townsfolk', TRUE, 'You do not know what your ability is. Each day, you may guess; you learn how accurate you are.', 'https://wiki.bloodontheclocktower.com/Amnesiac'),
(77, 'Atheist', 'Townsfolk', TRUE, 'There is no Demon. Executions fail. If the good team executes the Storyteller, they win.', 'https://wiki.bloodontheclocktower.com/Atheist'),
(78, 'Balloonist', 'Townsfolk', TRUE, 'Each night*, you learn 1 player of each character type. 1 is an Outsider.', 'https://wiki.bloodontheclocktower.com/Balloonist'),
(79, 'Banshee', 'Townsfolk', TRUE, 'Each night*, choose a player: they die. Evil assignments are told who you are.', 'https://wiki.bloodontheclocktower.com/Banshee'),
(80, 'Bounty Hunter', 'Townsfolk', TRUE, 'You start knowing 1 evil player. If you publicly name an evil player, they die.', 'https://wiki.bloodontheclocktower.com/Bounty_Hunter'),
(81, 'Cannibal', 'Townsfolk', TRUE, 'When a player is executed, you gain their ability until another player is executed.', 'https://wiki.bloodontheclocktower.com/Cannibal'),
(82, 'Choirboy', 'Townsfolk', TRUE, 'Each night*, you learn if the Demon is your neighbor.', 'https://wiki.bloodontheclocktower.com/Choirboy'),
(83, 'Cult Leader', 'Townsfolk', TRUE, 'Each night*, choose a player: if they are good, they join your cult. If all good assignments are in your cult, your team wins.', 'https://wiki.bloodontheclocktower.com/Cult_Leader'),
(84, 'Engineer', 'Townsfolk', TRUE, 'Once per game, at night*, choose which Minion is in play.', 'https://wiki.bloodontheclocktower.com/Engineer'),
(85, 'Farmer', 'Townsfolk', TRUE, 'If you die, another player becomes the Farmer.', 'https://wiki.bloodontheclocktower.com/Farmer'),
(86, 'Fisherman', 'Townsfolk', TRUE, 'Once per game, during the day, you may visit the Storyteller to gain advice.', 'https://wiki.bloodontheclocktower.com/Fisherman'),
(87, 'General', 'Townsfolk', TRUE, 'Each night*, you learn if good or evil is winning.', 'https://wiki.bloodontheclocktower.com/General'),
(88, 'High Priestess', 'Townsfolk', TRUE, 'Once per game, at night*, choose a dead player: they are resurrected.', 'https://wiki.bloodontheclocktower.com/High_Priestess'),
(89, 'Huntsman', 'Townsfolk', TRUE, 'Once per game, at night*, choose a player: if they are the Damsel, they become a Townsfolk.', 'https://wiki.bloodontheclocktower.com/Huntsman'),
(90, 'King', 'Townsfolk', TRUE, 'If you die, the game ends.', 'https://wiki.bloodontheclocktower.com/King'),
(91, 'Knight', 'Townsfolk', TRUE, 'If you die at night, the Demon dies.', 'https://wiki.bloodontheclocktower.com/Knight'),
(92, 'Lycanthrope', 'Townsfolk', TRUE, 'Each night*, choose a player: if they are evil, they die.', 'https://wiki.bloodontheclocktower.com/Lycanthrope'),
(93, 'Magician', 'Townsfolk', TRUE, 'You appear as a Minion to the Demon.', 'https://wiki.bloodontheclocktower.com/Magician'),
(94, 'Nightwatchman', 'Townsfolk', TRUE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Nightwatchman'),
(95, 'Noble', 'Townsfolk', TRUE, 'You start knowing 3 assignments, 1 of which is evil.', 'https://wiki.bloodontheclocktower.com/Noble'),
(96, 'Pixie', 'Townsfolk', TRUE, 'You start knowing 1 in-play Townsfolk. If you are mad about being this character, you gain their ability when they die.', 'https://wiki.bloodontheclocktower.com/Pixie'),
(97, 'Poppy Grower', 'Townsfolk', TRUE, 'Minions do not know each other or who the Demon is.', 'https://wiki.bloodontheclocktower.com/Poppy_Grower'),
(98, 'Preacher', 'Townsfolk', TRUE, 'Each night*, choose a player: they are drunk until dusk.', 'https://wiki.bloodontheclocktower.com/Preacher'),
(99, 'Shugenja', 'Townsfolk', TRUE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Shugenja'),
(100, 'Steward', 'Townsfolk', TRUE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Steward'),
(101, 'Village Idiot', 'Townsfolk', TRUE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Village_Idiot');

-- Outsiders
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(102, 'Damsel', 'Outsider', FALSE, 'If a Minion publicly guesses you, your team loses.', 'https://wiki.bloodontheclocktower.com/Damsel'),
(103, 'Golem', 'Outsider', FALSE, 'Each night*, you must choose a player: they die. You die if you choose an evil player.', 'https://wiki.bloodontheclocktower.com/Golem'),
(104, 'Hatter', 'Outsider', FALSE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Hatter'),
(105, 'Heretic', 'Outsider', FALSE, 'If the Demon dies, your team loses. If you die, your team wins.', 'https://wiki.bloodontheclocktower.com/Heretic'),
(106, 'Ogre', 'Outsider', FALSE, 'Each night*, choose a player: they die. You die if you choose an evil player.', 'https://wiki.bloodontheclocktower.com/Ogre'),
(107, 'Plague Doctor', 'Outsider', FALSE, 'Each night*, choose a player: they are poisoned until dusk.', 'https://wiki.bloodontheclocktower.com/Plague_Doctor'),
(108, 'Politician', 'Outsider', FALSE, 'If you are executed, your team wins.', 'https://wiki.bloodontheclocktower.com/Politician'),
(109, 'Puzzlemaster', 'Outsider', FALSE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Puzzlemaster'),
(110, 'Snitch', 'Outsider', FALSE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Snitch'),
(111, 'Zealot', 'Outsider', FALSE, 'Each night*, choose a player: they learn who you are.', 'https://wiki.bloodontheclocktower.com/Zealot');

-- Minions
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(112, 'Boffin', 'Minion', FALSE, 'Each night*, choose a player: they are poisoned until dusk.', 'https://wiki.bloodontheclocktower.com/Boffin'),
(113, 'Boomdandy', 'Minion', FALSE, 'If you are executed, all assignments die, and the game continues.', 'https://wiki.bloodontheclocktower.com/Boomdandy'),
(114, 'Fearmonger', 'Minion', FALSE, 'Each night*, choose a player: if they are not executed the next day, they die.', 'https://wiki.bloodontheclocktower.com/Fearmonger'),
(115, 'Goblin', 'Minion', FALSE, 'If you are executed, your team wins.', 'https://wiki.bloodontheclocktower.com/Goblin'),
(116, 'Harpy', 'Minion', FALSE, 'Each night*, choose a player: they die.', 'https://wiki.bloodontheclocktower.com/Harpy'),
(117, 'Marionette', 'Minion', FALSE, 'You appear as a Minion to the Demon.', 'https://wiki.bloodontheclocktower.com/Marionette'),
(118, 'Mezepheles', 'Minion', FALSE, 'Each night*, choose a player: they are poisoned until dusk.', 'https://wiki.bloodontheclocktower.com/Mezepheles'),
(119, 'Organ Grinder', 'Minion', FALSE, 'Each night*, choose a player: they die.', 'https://wiki.bloodontheclocktower.com/Organ_Grinder'),
(120, 'Psychopath', 'Minion', FALSE, 'Each day, you may choose a player: they die.', 'https://wiki.bloodontheclocktower.com/Psychopath'),
(121, 'Summoner', 'Minion', FALSE, 'Each night*, choose a player: they are poisoned until dusk.', 'https://wiki.bloodontheclocktower.com/Summoner'),
(122, 'Vizier', 'Minion', FALSE, 'Each night*, choose a player: they die.', 'https://wiki.bloodontheclocktower.com/Vizier'),
(123, 'Widow', 'Minion', FALSE, 'Once per game, at night, you may choose to learn all good characters in play.', 'https://wiki.bloodontheclocktower.com/Widow'),
(124, 'Wizard', 'Minion', FALSE, 'Each night*, choose a player: they are poisoned until dusk.', 'https://wiki.bloodontheclocktower.com/Wizard'),
(125, 'Xaan', 'Minion', FALSE, 'Each night*, choose a player: they die.', 'https://wiki.bloodontheclocktower.com/Xaan');

-- Demons
INSERT INTO Character (id, name, alignment, good, description, link_to_wiki) VALUES
(126, 'Al-Hadikhia', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Al-Hadikhia'),
(127, 'Kazali', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Kazali'),
(128, 'Legion', 'Demon', FALSE, 'There are multiple Demons. Evil wins if evil equals good. You do not know other Demons.', 'https://wiki.bloodontheclocktower.com/Legion'),
(129, 'Leviathan', 'Demon', FALSE, 'Each night*, choose a player: they die. If no player is executed today, all assignments die.', 'https://wiki.bloodontheclocktower.com/Leviathan'),
(130, 'Lil Monsta', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Lil%27_Monsta'),
(131, 'Lleech', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Lleech'),
(132, 'Lord of Typhon', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Lord_of_Typhon'),
(133, 'Ojo', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Ojo'),
(134, 'Riot', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Riot'),
(135, 'Yaggababble', 'Demon', FALSE, 'Each night*, choose a player: they die. If a player is executed today, you might change characters.', 'https://wiki.bloodontheclocktower.com/Yaggababble');

ALTER SEQUENCE CHARACTER_SEQ RESTART WITH ((SELECT MAX(id) FROM Character) + 51);


INSERT INTO Player (id, name, games_number, win_ratio, good_percentage) values
(1, 'Kamil', 0, 0, 0),
(2, 'Bartek', 0, 0, 0),
(3, 'Kamila', 0, 0, 0),
(4, 'Piter', 0, 0, 0),
(5, 'Przemek', 0, 0, 0),
(6, 'Basia', 0, 0, 0),
(7, 'Wojtek', 0, 0, 0),
(8, 'Robert', 0, 0, 0),
(9, 'Patrycja', 0, 0, 0),
(10, 'Łukasz', 0, 0, 0),
(11, 'Julia', 0, 0, 0),
(12, 'Michał', 0, 0, 0),
(13, 'Juliana', 0, 0, 0),
(14, 'Ali', 0, 0, 0);


INSERT INTO Script (id, name, times_played) values
(1, 'Trouble Brewing', 0),
(2, 'Bad Moon Rising', 0),
(3, 'Sects & Violets', 0);

INSERT INTO Script_Characters (script_id, characters_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(2, 23),
(2, 24),
(2, 25),
(2, 26),
(2, 27),
(2, 28),
(2, 29),
(2, 30),
(2, 31),
(2, 32),
(2, 33),
(2, 34),
(2, 35),
(2, 36),
(2, 37),
(2, 38),
(2, 39),
(2, 40),
(2, 41),
(2, 42),
(2, 43),
(2, 44),
(2, 45),
(2, 46),
(2, 47),
(3, 48),
(3, 49),
(3, 50),
(3, 51),
(3, 52),
(3, 53),
(3, 54),
(3, 55),
(3, 56),
(3, 57),
(3, 58),
(3, 59),
(3, 60),
(3, 61),
(3, 62),
(3, 63),
(3, 64),
(3, 65),
(3, 66),
(3, 67),
(3, 68),
(3, 69),
(3, 70),
(3, 71),
(3, 72);