package org.example.gui;

import org.example.dao.ProductDao;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

public class DeleteProductWindow {
    private JTextField nameField;

    public DeleteProductWindow(ProductDao productDao) {
        JFrame frame = new JFrame("Видалити товар");
        nameField = new JTextField(20);
        JButton deleteButton = new JButton("Видалити");

        deleteButton.addActionListener(e -> {
            String name = nameField.getText();
            Product product = productDao.getProductByName(name);
            if (product != null) {
                productDao.remove(product);
                // Вивід повідомлення про успішне видалення
                JOptionPane.showMessageDialog(null, "Товар видалено успішно");
            } else {
                // Вивід повідомлення, якщо товар не знайдено
                JOptionPane.showMessageDialog(null, "Товар не знайдено");
            }
            // Закриття вікна після видалення
            frame.dispose();
        });

        frame.setLayout(new GridLayout(2, 2));
        frame.add(new JLabel("Назва:"));
        frame.add(nameField);
        frame.add(deleteButton);

        frame.pack();
        frame.setVisible(true);
    }
}
