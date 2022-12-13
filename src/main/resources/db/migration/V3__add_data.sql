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
        'RAM 4 ГБ, eMMC 128 ГБ, Intel HD Graphics 500 , Windows 10 Home Single Language]', true, 18899,
        (SELECT id FROM subcategory_t WHERE subcategory_name='Ноутбуки и аксессуары'));

INSERT INTO image_t (file_name, product_id)
VALUES ('laptop-digma-eve-14-main.jpg',
        (SELECT id from product_t where product_name='14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-1.jpg',
        (SELECT id from product_t where product_name='14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-2.jpg',
        (SELECT id from product_t where product_name='14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-3.jpg',
        (SELECT id from product_t where product_name='14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-4.jpg',
        (SELECT id from product_t where product_name='14.1" Ноутбук Digma EVE 14 C411 серый'));