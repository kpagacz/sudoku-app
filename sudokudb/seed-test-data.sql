# Users
INSERT INTO Users (login, password)
VALUES ('test_login1', 'test_password1'),
       ('login', 'password'),
       ('test', 'test');

INSERT INTO Sudokus (title, cells, userLogin)
VALUES ('title1', '[[1,2,3,4,5,6,7,8,9],
                    [7,8,9,1,2,3,4,5,6],
                    [4,5,6,7,8,9,1,2,3],
                    [3,1,2,8,4,5,9,6,7],
                    [6,9,7,3,1,2,8,4,5],
                    [2,3,1,5,7,4,6,9,8],
                    [9,6,8,2,3,1,5,7,4],
                    [5,7,4,9,6,8,2,3,1]]', 'test'),
       ('title2', '[[1,2,3,4,5,6,7,8,9],
                     [7,8,9,1,2,3,4,5,6],
                     [4,5,6,7,8,9,1,2,3],
                     [3,1,2,8,4,5,9,6,7],
                     [6,9,7,3,1,2,8,4,5],
                     [2,3,1,5,7,4,6,9,8],
                     [9,6,8,2,3,1,5,7,4],
                     [5,7,4,9,6,8,2,3,1]]', 'test_login1');

INSERT INTO SolvedSudokus (sudokuId, userLogin)
VALUES (1, 'test');