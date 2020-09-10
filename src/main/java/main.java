import entities.HibernateSessionFactoryUtil;
import entities.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        int action=0;
        try {
            do {
                System.out.println("0. Вихід");
                System.out.println("1. Додати роль");
                System.out.println("2. Вивести список ролей");
                System.out.println("3. Видалення ролі");
                System.out.println("4. Оновлення ролі");
                action = Integer.parseInt(scanner.nextLine());
                switch (action){
                    case 0:{
                        break;
                    }
                    case 1:{
                        try {
                            Transaction transaction = session.beginTransaction();
                            String userRole;
                            System.out.println("Введіть роль: ");
                            userRole = scanner.nextLine();
                            Role role = new Role(userRole);
                            session.save(role);
                            transaction.commit();
                            System.out.println("Додавання успішне");
                        }
                        catch (Exception e){
                            System.out.println("Схоже, що ви ввели неправильні дані. Спробуйте ще раз");
                        }
                        break;
                    }
                    case 2:{
                        try {
                            Transaction transaction = session.beginTransaction();
                            Query query = session.createQuery("FROM Role");
                            List<Role> result = query.list();
                            for(Role role:result){
                                System.out.println("Id: " + role.getId() + " Ім'я: " + role.getName());
                            }
                            transaction.commit();
                        }
                        catch (Exception e){
                            System.out.println("Схоже, що ви ввели неправильні дані. Спробуйте ще раз");
                        }
                        break;
                    }
                    case 3:{
                        try{
                            Transaction transaction = session.beginTransaction();
                            int role_id = 0;
                            System.out.println("Введіть id ролі: ");
                            role_id = Integer.parseInt(scanner.nextLine());
                            Query query = session.createQuery("DELETE FROM Role " +
                                    "WHERE id = :id");
                            query.setParameter("id", role_id);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Видалення успішне");
                        }
                        catch (Exception e){
                            System.out.println("Схоже, що ви ввели неправильні дані. Спробуйте ще раз");
                        }
                        break;
                    }
                    case 4:{
                        try{
                            Transaction transaction = session.beginTransaction();
                            int role_id = 0;
                            String newName = "";
                            System.out.println("Введіть id ролі: ");
                            role_id = Integer.parseInt(scanner.nextLine());
                            System.out.println("Введіть нове ім'я ролі: ");
                            newName = scanner.nextLine();
                            Query query = session.createQuery("UPDATE Role set name = :name " +
                                    "WHERE id = :id");
                            query.setParameter("id", role_id);
                            query.setParameter("name", newName);
                            int result = query.executeUpdate();
                            transaction.commit();
                            System.out.println("Оновлення успішне");
                        }
                        catch (Exception e){
                            System.out.println("Схоже, що ви ввели неправильні дані. Спробуйте ще раз");
                        }
                        break;
                    }
                    default:{
                        System.out.println("Введіть правильний номер пункта меню");
                    }
                }
            }while(action != 0);
        }
        catch (Exception e){
            System.out.println("Отвал");
        }
        session.close();
    }
}
