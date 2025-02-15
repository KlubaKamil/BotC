-- Townsfolk
INSERT INTO Character (name, alignment, good, description, linkToWiki) VALUES
('Washerwoman', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Townsfolk.', 'https://wiki.bloodontheclocktower.com/Washerwoman'),
('Librarian', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Outsider, or that zero are in play.', 'https://wiki.bloodontheclocktower.com/Librarian'),
('Investigator', 'Townsfolk', TRUE, 'You start knowing that 1 of 2 assignments is a particular Minion.', 'https://wiki.bloodontheclocktower.com/Investigator'),
('Chef', 'Townsfolk', TRUE, 'You start knowing how many pairs of evil assignments there are.', 'https://wiki.bloodontheclocktower.com/Chef'),
('Empath', 'Townsfolk', TRUE, 'Each night, you learn how many of your 2 alive neighbors are evil.', 'https://wiki.bloodontheclocktower.com/Empath'),
('Fortune Teller', 'Townsfolk', TRUE, 'Each night, choose 2 assignments: you learn if either is a Demon. There is a good player that always registers as a Demon to you.', 'https://wiki.bloodontheclocktower.com/Fortune_Teller'),
('Undertaker', 'Townsfolk', TRUE, 'Each night*, you learn which character died by execution today.', 'https://wiki.bloodontheclocktower.com/Undertaker'),
('Monk', 'Townsfolk', TRUE, 'Each night*, choose a player (not yourself): they are safe from the Demon tonight.', 'https://wiki.bloodontheclocktower.com/Monk'),
('Ravenkeeper', 'Townsfolk', TRUE, 'If you die at night, you are woken to choose a player: you learn their character.', 'https://wiki.bloodontheclocktower.com/Ravenkeeper'),
('Virgin', 'Townsfolk', TRUE, 'The first time you are nominated, if the nominator is a Townsfolk, they are executed immediately.', 'https://wiki.bloodontheclocktower.com/Virgin'),
('Slayer', 'Townsfolk', TRUE, 'Once per game, during the day, publicly choose a player: if they are the Demon, they die.', 'https://wiki.bloodontheclocktower.com/Slayer'),
('Soldier', 'Townsfolk', TRUE, 'You are safe from the Demon.', 'https://wiki.bloodontheclocktower.com/Soldier'),
('Mayor', 'Townsfolk', TRUE, 'If only 3 assignments live & no execution occurs, your team wins. If you die at night, another player might die instead.', 'https://wiki.bloodontheclocktower.com/Mayor');

-- Outsiders
INSERT INTO Character (name, alignment, good, description, linkToWiki) VALUES
('Butler', 'Outsider', TRUE, 'Each night, choose a player (not yourself): tomorrow, you may only vote if they are voting too.', 'https://wiki.bloodontheclocktower.com/Butler'),
('Drunk', 'Outsider', TRUE, 'You do not know you are the Drunk. You think you are a Townsfolk character, but you are not.', 'https://wiki.bloodontheclocktower.com/Drunk'),
('Recluse', 'Outsider', TRUE, 'You might register as evil & as a Minion or Demon, even if dead.', 'https://wiki.bloodontheclocktower.com/Recluse'),
('Saint', 'Outsider', TRUE, 'If you die by execution, your team loses.', 'https://wiki.bloodontheclocktower.com/Saint');

-- Minions
INSERT INTO Character (name, alignment, good, description, linkToWiki) VALUES
('Poisoner', 'Minion', FALSE, 'Each night, choose a player: they are poisoned tonight and tomorrow day.', 'https://wiki.bloodontheclocktower.com/Poisoner'),
('Spy', 'Minion', FALSE, 'You start knowing which characters are in play & where. You might register as good & as a Townsfolk.', 'https://wiki.bloodontheclocktower.com/Spy'),
('Scarlet Woman', 'Minion', FALSE, 'If there are 5 or more assignments alive & the Demon dies, you become the Demon.', 'https://wiki.bloodontheclocktower.com/Scarlet_Woman'),
('Baron', 'Minion', FALSE, 'There are 2 extra Outsiders in play.', 'https://wiki.bloodontheclocktower.com/Baron');

-- Demon
INSERT INTO Character (name, alignment, good, description, linkToWiki) VALUES
('Imp', 'Demon', FALSE, 'Each night*, choose a player: they die. If you kill yourself this way, a Minion becomes the Imp.', 'https://wiki.bloodontheclocktower.com/Imp');