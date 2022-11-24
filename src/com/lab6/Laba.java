package com.lab6;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Laba {


  public static void main(String[] args) {
    UI myUI = new UI();
    myUI.menu();

  }

}

interface menuEnum {

  int MAKE_INT_ARRAY = 1,
      MAKE_STRING_ARRAY = 2,
      EXIT = 3;

  int PRINT_ARRAY = 1,
      GET_BY_INDEX = 2,
      SET_BY_INDEX = 3,
      CHANGE_START_INDEX = 4,
      CHANGE_END_INDEX = 5,
      INVERT_ARRAY = 6,
      CONCAT_ARRAY = 7,
      FIND_ITEM = 8,

  INSERT_SEQ = 9,
      PRINT_BOUNDED = 10,
      ARR_MENU_EXIT = 11;
}


class UI implements menuEnum {

  private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
  private final Scanner in = new Scanner(System.in);
  private final Inputer inp = new Inputer();
  private MyArray<String> strArray;
  private MyArray<Integer> intArray;


  public void menu() {

    int choice;

    do {
      this.out.println("""
          1. Создать массив типа Int
          2. Создать массив типа String
          3. Выход
          """);

      choice = inp.getInt();
      switch (choice) {
        case MAKE_INT_ARRAY -> setIntArray();
        case MAKE_STRING_ARRAY -> setStringArray();
        default -> {
          if (choice != EXIT) {
            this.out.println("Некорректный ввод");
          }
        }
      }

    } while (choice != EXIT);
  }

  private void setIntArray() {
    String answ = null;
    while (!Objects.equals(answ, "y") && !Objects.equals(answ, "n")) {
      out.println("Создаем пустой массив? y/n");
      answ = inp.getString();
    }
    if (Objects.equals(answ, "y")) {
      intArray = new MyArray<>();
      arrayMenu("int");
      return;
    }
    while (true) {
      out.println("Введите числа через пробел");
      try {
        Integer[] ints = inp.getIntLine();
        intArray = new MyArray<>(ints);
        arrayMenu("int");
        return;
      } catch (NumberFormatException e) {
        out.println("Некорректное значение " + e.getMessage());
      }
    }
  }

  private void setStringArray() {
    String answ = null;
    while (!Objects.equals(answ, "y") && !Objects.equals(answ, "n")) {
      out.println("Создаем пустой массив? y/n");
      answ = inp.getString();
    }
    if (Objects.equals(answ, "y")) {
      strArray = new MyArray<>();
      arrayMenu("str");
      return;
    }
    while (true) {
      out.println("Введите строки через пробел");
      try {
        String[] strings = inp.getStrLine();
        strArray = new MyArray<>(strings);
        arrayMenu("str");
        return;
      } catch (NullPointerException e) {
        out.println("Некорректное значение");
      }
    }

  }

  private void arrayMenu(String arrType) {
    int choice;

    do {
      this.out.println("""
          1. Вывести массив
          2. Получить значение элемента по индексу
          3. Установить значение элемента по индексу
          4. Изменить начальный индекс
          5. Изменить конечный индекс
          6. Инвертировать массив
          7. Конкатенация текущего массива с введенным
          8. Найти индекс первого вохждения
          9. Вставить последовательность после элемента с индексом
          10. Вывести часть массива с заданными границами
          11. Выход
          """);

      choice = inp.getInt();
      switch (choice) {
        case PRINT_ARRAY -> printArray(arrType);
        case GET_BY_INDEX -> getByIndex(arrType);
        case SET_BY_INDEX -> setByIndex(arrType);
        case CHANGE_START_INDEX -> changeStartIndex(arrType);
        case CHANGE_END_INDEX -> changeEndIndex(arrType);
        case INVERT_ARRAY -> invertArray(arrType);
        case CONCAT_ARRAY -> concatArray(arrType);
        case INSERT_SEQ -> insertSequence(arrType);
        case FIND_ITEM -> findItem(arrType);
        case PRINT_BOUNDED -> printBoundedArray(arrType);
        default -> {
          if (choice != ARR_MENU_EXIT) {
            this.out.println("Некорректный ввод");
          }
        }
      }

    } while (choice != ARR_MENU_EXIT);

  }

  private void printArray(String arrType) {
    if (Objects.equals(arrType, "str")) {
      out.println(strArray);
    } else {
      out.println(intArray);
    }
  }

  private void getByIndex(String arrType) {
    out.println("Введите индекс:");
    int index = inp.getInt();
    try {
      if (Objects.equals(arrType, "str")) {
        out.println(strArray.getItemAt(index));
      } else {
        out.println(intArray.getItemAt(index));
      }
    } catch (IndexOutOfBoundsException e) {
      out.println("Некорректный индекс: " + e.getMessage());
    }
  }

  private void setByIndex(String arrType) {
    out.println("Введите индекс:");
    int index = inp.getInt();
    out.println("Введите значение:");
    try {
      if (Objects.equals(arrType, "str")) {
        strArray.setItemAt(index, inp.getString());
      } else {
        int newInt = inp.getInt();
        if (newInt == -1) {
          throw new NumberFormatException();
        }
        intArray.setItemAt(index, newInt);
      }
    } catch (IndexOutOfBoundsException e) {
      out.println("Некорректный индекс: " + e.getMessage());
    } catch (NumberFormatException e) {
      out.println("Некорректное значение");
    }
  }

  private void changeStartIndex(String arrType) {
    out.println("Введите индекс:");
    int index = inp.getInt();
    if (index == -1) {
      out.println("Некорректный индекс");
      return;
    }
    if (Objects.equals(arrType, "str")) {
      strArray.setStartIndex(index);
    } else {
      intArray.setStartIndex(index);
    }
  }

  private void changeEndIndex(String arrType) {
    out.println("Введите индекс:");
    int index = inp.getInt();
    if (index == -1) {
      out.println("Некорректный индекс");
      return;
    }
    if (Objects.equals(arrType, "str")) {
      strArray.setEndIndex(index);
    } else {
      intArray.setEndIndex(index);
    }
  }

  private void invertArray(String arrType) {
    if (Objects.equals(arrType, "str")) {
      strArray.invert();
    } else {
      intArray.invert();
    }
  }

  private void concatArray(String arrType) {
    if (Objects.equals(arrType, "str")) {
      while (true) {
        out.println("Введите строки через пробел");
        try {
          String[] strings = inp.getStrLine();
          strArray.extend(strings);
          return;
        } catch (NullPointerException e) {
          out.println("Некорректное значение");
        }
      }
    } else {
      while (true) {
        out.println("Введите числа через пробел");
        try {
          Integer[] ints = inp.getIntLine();
          intArray.extend(ints);
          return;
        } catch (NumberFormatException e) {
          out.println("Некорректное значение " + e.getMessage());
        }
      }
    }
  }

  private void findItem(String arrType) {
    if (Objects.equals(arrType, "str")) {
      out.println(strArray.find(inp.getString()));
    } else {
      out.println(intArray.find(inp.getInt()));
    }
  }

  private void insertSequence(String arrType) {
    out.println("ВВедите индекс");
    int index = inp.getInt();

    if (Objects.equals(arrType, "str")) {
      while (true) {
        out.println("Введите строки через пробел");
        try {
          String[] strings = inp.getStrLine();
          strArray.insert(index, strings);
          return;
        } catch (NullPointerException e) {
          out.println("Некорректное значение");
        } catch (IndexOutOfBoundsException e) {
          out.println("Некорректный индекс " + e.getMessage());
        }
      }
    } else {
      while (true) {
        out.println("Введите числа через пробел");
        try {
          Integer[] ints = inp.getIntLine();
          intArray.insert(index, ints);
          return;
        } catch (NumberFormatException e) {
          out.println("Некорректное значение " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
          out.println("Некорректный индекс " + e.getMessage());
        }
      }
    }

  }

  private void printBoundedArray(String arrType) {
    out.println("Введите левую границу");
    int left = inp.getInt();
    out.println("Введите правую границу");
    int right = inp.getInt();
    if (left == -1 || right == -1) {
      out.println("Некорректное значение");
    }
    try {
      if (Objects.equals(arrType, "str")) {
        out.println(strArray.toString(left, right));
      } else {
        out.println(intArray.toString(left, right));
      }
    } catch (IndexOutOfBoundsException e) {
      out.println("Некорректный индекс: " + e);
    }
  }


}