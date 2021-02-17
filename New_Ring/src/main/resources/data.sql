INSERT INTO department
(id,name) VALUES
(0,'未選択')
ON CONFLICT (id)
DO UPDATE SET name = '未選択';

INSERT INTO department
(id,name) VALUES
(1,'人事部')
ON CONFLICT (id)
DO UPDATE SET name = '人事部';

INSERT INTO department
(id,name) VALUES
(2,'営業部')
ON CONFLICT (id)
DO UPDATE SET name = '営業部';

INSERT INTO department
(id,name) VALUES
(3,'IT事業部')
ON CONFLICT (id)
DO UPDATE SET name = 'IT事業部';

INSERT INTO department
(id,name) VALUES
(4,'研修生')
ON CONFLICT (id)
DO UPDATE SET name = '研修生';

INSERT INTO department
(id,name) VALUES
(5,'インターン')
ON CONFLICT (id)
DO UPDATE SET name = 'インターン';