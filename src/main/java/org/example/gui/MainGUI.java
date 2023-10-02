package org.example.gui;

import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGUI {
    private JFrame frame;
    private DefaultListModel<Product> productListModel;
    private JList<Product> productList;
    private ProductDao productDao;
    private JTextField searchField;

    public MainGUI(ProductDao productDao) {
        this.productDao = productDao;
        frame = new JFrame("Список товарів");
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Пошук");
        JButton cancelButton = new JButton("Скасувати пошук");

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                List<Product> searchResults = productDao.searchProducts(keyword);
                updateProductList(searchResults);
            }
        });

        cancelButton.addActionListener(e -> {
            searchField.setText("");
            updateProductList();
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Пошук:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(cancelButton);

        JButton addButton = new JButton("Додати товар");
        JButton subtractButton = new JButton("Відняти товар");
        JButton editButton = new JButton("Редагувати товар");
        JButton createButton = new JButton("Створити товар");
        JButton deleteButton = new JButton("Видалити товар");

        addButton.addActionListener(e -> {
            Product selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                String quantityStr = JOptionPane.showInputDialog("Введіть кількість для додавання:");
                try {
                    int quantityToAdd = Integer.parseInt(quantityStr);
                    selectedProduct.setQuantity(selectedProduct.getQuantity() + quantityToAdd);
                    productDao.update(selectedProduct);
                    updateProductList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Невірний формат введених даних.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Виберіть товар для додавання.");
            }
        });

        subtractButton.addActionListener(e -> {
            Product selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                String quantityStr = JOptionPane.showInputDialog("Введіть кількість для віднімання:");
                try {
                    int quantityToSubtract = Integer.parseInt(quantityStr);
                    if (selectedProduct.getQuantity() >= quantityToSubtract) {
                        selectedProduct.setQuantity(selectedProduct.getQuantity() - quantityToSubtract);
                        productDao.update(selectedProduct);
                        updateProductList();
                    } else {
                        JOptionPane.showMessageDialog(null, "Недостатньо товару на складі.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Невірний формат введених даних.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Виберіть товар для віднімання.");
            }
        });

        editButton.addActionListener(e -> {
            Product selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                new EditProductWindow(productDao, selectedProduct);
                updateProductList();
            } else {
                JOptionPane.showMessageDialog(null, "Виберіть товар для редагування.");
            }
        });

        createButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Введіть назву товару:");
            String serialNumber = JOptionPane.showInputDialog("Введіть серійний номер товару:");
            try {
                int quantity = Integer.parseInt(JOptionPane.showInputDialog("Введіть кількість товару:"));
                Product existingProduct = productDao.getProductBySerialNumber(serialNumber);
                if (existingProduct == null) {
                    Product newProduct = new Product();
                    newProduct.setName(name);
                    newProduct.setSerialNumber(serialNumber);
                    newProduct.setQuantity(quantity);
                    productDao.create(newProduct);
                    updateProductList();
                } else {
                    JOptionPane.showMessageDialog(null, "Товар з таким серійним номером вже існує.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Невірний формат введених даних.");
            }
        });

        deleteButton.addActionListener(e -> {
            Product selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                int confirm = JOptionPane.showConfirmDialog(null, "Ви впевнені, що хочете видалити товар?");
                if (confirm == JOptionPane.YES_OPTION) {
                    productDao.remove(selectedProduct);
                    updateProductList();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Виберіть товар для видалення.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(editButton);
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);

        frame.setLayout(new BorderLayout());
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(productList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        updateProductList();
    }

    // Оновлений метод оновлення списку товарів
    private void updateProductList(List<Product> products) {
        productListModel.clear();
        for (Product product : products) {
            productListModel.addElement(product);
        }
    }

    // Оновлений метод оновлення списку товарів (без параметрів, відображення всіх товарів)
    private void updateProductList() {
        List<Product> products = productDao.getAll();
        updateProductList(products);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductDao productDao = new ProductDaoImpl();
            new MainGUI(productDao);
        });
    }
}
