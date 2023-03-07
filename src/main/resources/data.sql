INSERT INTO member (member_id, name, nickname, introduce)
VALUES (100, 'USER1', 'NICK1', ''),
       (101, 'USER2', 'NICK2', '');

INSERT INTO article (article_id, title, article, bookmark_hits, hits, member_id, created_at, code)
VALUES (100, 'Test title 1', 'Test content 1', 0, 0, 100, '2023-02-01 23:05:28.010856', 'int main() { <br>System.out.println("hello World");<br>}'),
       (101, 'Test title 2', 'Test content 2', 0, 0, 100, '2023-02-02 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (102, 'Test title 3', 'Test content 3', 0, 0, 101, '2023-02-03 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (103, 'Test title 4', 'Test content 4', 0, 0, 100, '2023-02-04 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (104, 'Test title 5', 'Test content 5', 0, 0, 101, '2023-02-05 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (105, 'Test title 6', 'Test content 6', 0, 0, 101, '2023-02-06 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (106, 'Test title 7', 'Test content 7', 0, 0, 101, '2023-02-07 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (107, 'Test title 8', 'Test content 8', 0, 0, 100, '2023-02-08 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (108, 'Test title 9', 'Test content 9', 0, 0, 101, '2023-02-09 23:05:28.010856', 'int main() { System.out.println("hello World"); }'),
       (109, 'Test title 10', 'Test content 10', 0, 0, 100, '2023-02-10 23:05:28.010856', 'int main() { System.out.println("hello World"); }');
