INSERT INTO category_t (category_name, cipher, file_name)
VALUES ('Компьютерная техника', 'computer-technology', 'computer technology.png'),
       ('Комплектующие для ПК', 'pc-accessories', 'PC accessories.png'),
       ('Смартфоны и фототехника', 'smartphones-and-photo-equipment', 'Smartphones and photo equipment.png'),
       ('ТВ, консоли и аудио', 'tv-consoles-and-audio', 'TV, consoles and audio.png');

INSERT INTO subcategory_t (subcategory_name, cipher, file_name, category_id)
VALUES ('Ноутбуки и аксессуары', 'laptops-and-accessories', 'Laptops and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника')),
       ('Компьютеры и ПО', 'computers-and-software', 'Computers and software.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника')),
       ('Периферия и аксессуары', 'peripherals-and-accessories', 'Peripherals and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника'));

INSERT INTO product_t (product_name, cipher, description, present, price, subcategory_id)
VALUES ('14.1" Ноутбук Digma EVE 14 C411 серый', 'laptop-digma-eve-14',
        '14.1" Ноутбук Digma EVE 14 C411 серый [Full HD (1920x1080), IPS, Intel Celeron 3350, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, eMMC 128 ГБ, Intel HD Graphics 500 , Windows 10 Home Single Language]', TRUE, 18899,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('13.3" Ноутбук Irbis NB77 черный', 'laptop-irbis-nb77',
        '13.3" Ноутбук Irbis NB77 черный [HD (1366x768), TN+film, Intel Atom Z3735F, ядра: 4 х 1.33 ГГц, RAM 2 ГБ, ' ||
        'eMMC 32 ГБ, Intel HD Graphics , Windows 10 Home Single Language]', TRUE, 14999,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('14.1" Ноутбук Prestigio SmartBook 133 C4 серый', 'laptop-prestigio-smartbook-133',
        '14.1" Ноутбук Prestigio SmartBook 133 C4 серый [HD (1366x768), TN+film, AMD A4-9120e, ядра: 2 х 1.5 ГГц, ' ||
        'RAM 4 ГБ, eMMC 64 ГБ, AMD Radeon R3 , Windows 10 Pro]', TRUE, 18999,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный', 'asus-laptop-11',
        '11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный [HD (1366x768), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, eMMC 128 ГБ, Intel UHD Graphics 600 , Windows 10 Home Single Language]', TRUE,
        23999, (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('14" Ноутбук Lenovo V14 IGL серый', 'laptop-lenovo-v11',
        '14" Ноутбук Lenovo V14 IGL серый [Full HD (1920x1080), TN+film, Intel Celeron N4020, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics 600 , без ОС]', TRUE, 23999,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый', 'asus-laptop-14',
        '14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый [Full HD (1920x1080), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics , Windows 11 Home Single Language]', false, 24299,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный', 'laptop-acer-extensa-15',
        '15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный [Full HD (1920x1080), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics , без ОС]', true, 24299,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('15.6" Ноутбук HP 250 G7 серый', 'laptop-hp-250-g7',
        '15.6" Ноутбук HP 250 G7 серый [Full HD (1920x1080), SVA (TN+film), Intel Celeron N4020, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics 600 , без ОС]', true, 25199,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый', 'laptop-lenovo-ideapad-3-15ada05',
        '15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый [HD (1366x768), TN+film, AMD Athlon Silver 3050U, ' ||
        'ядра: 2 х 2.3 ГГц, RAM 4 ГБ, HDD 1000 ГБ, AMD Radeon Graphics , без ОС]', true, 25199,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('14" Ноутбук ASUS VivoBook 14 E410MA-BV1502W черный', 'laptop-asus-vivobook-14',
        '14" Ноутбук ASUS Vivobook Go 14 E410MA-BV1521W черный [HD (1366x768), TN+film, Intel Pentium Silver N5030, ' ||
        'ядра: 4 х 1.1 ГГц, RAM 4 ГБ, eMMC 128 ГБ, Intel UHD Graphics 605 , Windows 11 Home Single', true, 26099,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары')),
       ('15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный', 'laptop-acer-aspire-3',
        '15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный [Full HD (1920x1080), TN+film, AMD Athlon Silver 3050U, ' ||
        'ядра: 2 х 2.3 ГГц, RAM 4 ГБ, SSD 256 ГБ, AMD Radeon Graphics , без ОС]', true, 26999,
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки и аксессуары'));

INSERT INTO image_t (file_name, product_id)
VALUES ('laptop-digma-eve-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-1.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-2.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-3.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-4.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-irbis-nb77-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '13.3" Ноутбук Irbis NB77 черный')),
       ('laptop-prestigio-smartbook-133-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Prestigio SmartBook 133 C4 серый')),
       ('asus-laptop-11-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный')),
       ('laptop-lenovo-v11-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук Lenovo V14 IGL серый')),
       ('asus-laptop-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый')),
       ('laptop-acer-extensa-15-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный')),
       ('laptop-hp-250-g7-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук HP 250 G7 серый')),
       ('laptop-lenovo-ideapad-3-15ada05-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый')),
       ('laptop-asus-vivobook-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук ASUS VivoBook 14 E410MA-BV1502W черный')),
       ('laptop-acer-aspire-3-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный'));
