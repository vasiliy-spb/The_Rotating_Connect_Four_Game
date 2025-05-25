package dev.cheercode.connectfour.renderer;

import dev.cheercode.connectfour.model.Disc;
import dev.cheercode.connectfour.model.board.Board;

//public class RendererForIdea implements Renderer {
//    // ANSI escape последовательности для форматирования
//    private static final String CIRCLE = "⬤";
//    private static final String BACKGROUND_COLOR = "\u001B[7m";
//    private static final String BORDER_COLOR = "\u001B[34m";
//    private static final String RESET = "\u001B[0m";
//    private static final String RED_BG = "\u001B[101m";
//    private static final String BLUE_BG = "\u001B[104m";
//    private static final String YELLOW_BG = "\u001B[103m";
//    private static final String GREEN_BG = "\u001B[102m";
//
//    // Шаблоны для форматирования строк
//    private static final String SLOT_TEMPLATE = " %s |";
//    private static final String SLOT_TEMPLATE_BG = BACKGROUND_COLOR + BORDER_COLOR + " %s |";
//    private static final String FOOTER_SLOT_TEMPLATE = " %s   ";
//    private static final String NEWLINE = "\n";
//    private static final String EMPTY_SLOT_BG = "    " + BACKGROUND_COLOR + BORDER_COLOR + "|";
//    private static final String EMPTY_SLOT = "    |";
//    private static final String ROW_NUMBER_TEMPLATE_BG = "%2d " + BACKGROUND_COLOR + BORDER_COLOR + "|";
//    private static final String ROW_NUMBER_TEMPLATE = "%2d |";
//    private static final String SEPARATOR_SPACE_BG = "   " + BACKGROUND_COLOR + BORDER_COLOR + "+";
//    private static final String SEPARATOR_SPACE = "   +";
//    private static final String SEPARATOR_SLOT_BG = BACKGROUND_COLOR + BORDER_COLOR + "----+";
//    private static final String SEPARATOR_SLOT_TAIL_BG = "----" + BACKGROUND_COLOR + BORDER_COLOR + "+";
//    private static final String SEPARATOR_SLOT = "----+";
//    private static final String FOOTER_SPACE = "     ";
//
//    @Override
//    public void show(Board board) {
//        int height = board.getHeight();
//        int width = board.getWidth();
//        StringBuilder field = new StringBuilder();
//
//        // Заголовок с разделительной линией
//        field.append(buildSeparatorLine(-1, width, board));
//
//        // Проходим по строкам доски
//        for (int row = 0; row < height; row++) {
//            // Вывод номера строки с учётом фона
//            if (board.isOnField(row, 0)) {
//                field.append(String.format(ROW_NUMBER_TEMPLATE_BG, row + 1))
//                        .append(BACKGROUND_COLOR);
//            } else {
//                field.append(String.format(ROW_NUMBER_TEMPLATE, row + 1));
//            }
//
//            // Отрисовка каждого столбца в строке
//            for (int col = 0; col < width; col++) {
//                if (!board.isOnField(row, col)) {
//                    // Если ячейка вне поля, обрабатываем соседний столбец для оформления
//                    if (col + 1 < width && board.isOnField(row, col + 1)) {
//                        field.append(RESET).append(EMPTY_SLOT_BG)
//                                .append(BACKGROUND_COLOR).append(BORDER_COLOR);
//                    } else {
//                        field.append(RESET).append(EMPTY_SLOT)
//                                .append(BACKGROUND_COLOR).append(BORDER_COLOR);
//                    }
//                    continue;
//                }
//
//                if (board.isEmptySlot(row, col)) {
//                    field.append(BACKGROUND_COLOR).append(BORDER_COLOR).append(EMPTY_SLOT);
//                } else {
//                    Disc disc = board.get(row, col);
//                    String sprite = getColoredCircle(disc);
//                    field.append(BACKGROUND_COLOR)
//                            .append(String.format(SLOT_TEMPLATE_BG, sprite))
//                            .append(RESET);
//                }
//            }
//            field.append(RESET).append(NEWLINE);
//            field.append(buildSeparatorLine(row, width, board));
//        }
//        field.append(buildFooter(width));
//
//        System.out.println(field);
//    }
//
//    /**
//     * Формирует разделительную строку (верхнюю для заголовка или между строками).
//     *
//     * @param row   номер текущей строки (для заголовка используется -1)
//     * @param width ширина доски
//     * @param board используемая доска
//     * @return строка-разделитель
//     */
//    private String buildSeparatorLine(int row, int width, Board board) {
//        StringBuilder line = new StringBuilder();
//        int nextRow = row + 1;
//        // Определяем, нужен ли фон для первого разделительного символа
//        boolean useBgForFirst = (row < 0)
//                ? (nextRow < board.getHeight() && board.isOnField(nextRow, 0))
//                : (board.isOnField(row, 0) || (nextRow < board.getHeight() && board.isOnField(nextRow, 0)));
//
//        line.append(useBgForFirst ? SEPARATOR_SPACE_BG : SEPARATOR_SPACE);
//
//        for (int col = 0; col < width; col++) {
//            boolean hasBackground;
//            if (row < 0) {
//                hasBackground = (nextRow < board.getHeight() && board.isOnField(nextRow, col));
//            } else {
//                hasBackground = (board.isOnField(row, col) || (nextRow < board.getHeight() && board.isOnField(nextRow, col)));
//            }
//            if (hasBackground) {
//                line.append(SEPARATOR_SLOT_BG);
//            } else {
//                boolean hasTailBg;
//                if (row < 0) {
//                    hasTailBg = board.isOnField(nextRow, col + 1);
//                } else {
//                    hasTailBg = (col + 1 < width &&
//                                 (board.isOnField(row, col + 1) ||
//                                  (row < board.getHeight() - 1 && board.isOnField(nextRow, col + 1))));
//                }
//                line.append(hasTailBg ? RESET + SEPARATOR_SLOT_TAIL_BG : RESET + SEPARATOR_SLOT);
//            }
//        }
//        line.append(RESET).append(NEWLINE);
//        return line.toString();
//    }
//
//    /**
//     * Возвращает цветной символ в зависимости от цвета диска.
//     *
//     * @param disc объект диска
//     * @return цветной символ
//     */
//    private String getColoredCircle(Disc disc) {
//        String colorBg;
//        switch (disc) {
//            case RED:
//                colorBg = RED_BG;
//                break;
//            case BLUE:
//                colorBg = BLUE_BG;
//                break;
//            case YELLOW:
//                colorBg = YELLOW_BG;
//                break;
//            case GREEN:
//                colorBg = GREEN_BG;
//                break;
//            default:
//                throw new IllegalArgumentException("Неизвестный цвет диска: " + disc);
//        }
//        return colorBg + CIRCLE + RESET + BACKGROUND_COLOR + BORDER_COLOR;
//    }
//
//    /**
//     * Формирует футер с номерами столбцов.
//     *
//     * @param width ширина доски
//     * @return строка-футер
//     */
//    private String buildFooter(int width) {
//        StringBuilder footer = new StringBuilder(FOOTER_SPACE);
//        for (int col = 1; col <= width; col++) {
//            footer.append(String.format(FOOTER_SLOT_TEMPLATE, col));
//        }
//        footer.append(NEWLINE);
//        return footer.toString();
//    }
//}

public class RendererForIdea implements Renderer {
    private static final String circle = "⬤";
    private static final String backgroundColor = "\u001B[7m";
    private static final String borderColor = "\u001B[34m";
    private static final String reset = "\u001B[0m";
    private static final String red = "\u001B[101m";
    private static final String blue = "\u001B[104m";
    private static final String yellow = "\u001B[103m";
    private static final String green = "\u001B[102m";
    private static final String slotTemplate = " %s |";
    private static final String slotTemplateWithBackground = backgroundColor + borderColor + " %s |";
    private static final String footerSlotTemplate = " %s   ";
    private static final String newLine = "\n";
    private static final String emptySlotWithBackground = "    " + backgroundColor + borderColor + "|";
    private static final String emptySlot = "    |";
    private static final String rowNumberTemplateWithBackground = "%2d " + backgroundColor + borderColor + "|";
    private static final String rowNumberTemplate = "%2d |";
    private static final String separatorSpaceWithBackground = "   " + backgroundColor + borderColor + "+";
    private static final String separatorSpace = "   +";
    private static final String separatorSlotWithBackground = backgroundColor + borderColor + "----+";
    private static final String separatorSlotWithTailBackground = "----" + backgroundColor + borderColor + "+";
    private static final String separatorSlot = "----+";
    private static final String footerSpace = "     ";

    @Override
    public void show(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        StringBuilder field = new StringBuilder();

        field.append(getSeparatorLine(-1, width, board));

        for (int row = 0; row < height; row++) {
            if (!board.isOnField(row, 0)) {
                field.append(String.format(rowNumberTemplate, row + 1));
            } else {
                field.append(String.format(rowNumberTemplateWithBackground, row + 1));
                field.append(backgroundColor);
            }
            for (int col = 0; col < width; col++) {
                if (!board.isOnField(row, col)) {
                    if (col + 1 < width && board.isOnField(row, col + 1)) {
                        field.append(reset + emptySlotWithBackground + backgroundColor + borderColor);
                    } else {
                        field.append(reset + emptySlot + backgroundColor + borderColor);
                    }
                    continue;
                }
                if (board.isEmptySlot(row, col)) {
                    field.append(backgroundColor + borderColor + emptySlot);
                    continue;
                }
                Disc disc = board.get(row, col);
                String sprite = getColoredCircle(disc);
                field.append(backgroundColor)
                        .append(String.format(slotTemplateWithBackground, sprite))
                        .append(reset);
            }
            field.append(reset);
            field.append(newLine);

            field.append(getSeparatorLine(row, width, board));
        }

        field.append(getFooter(width));

        System.out.println(field);
    }

    private StringBuilder getSeparatorLine(int row, int width, Board board) {
        StringBuilder line = new StringBuilder();

        if (row < 0) {
            if (row + 1 < board.getHeight() && board.isOnField(row + 1, 0)) {
                line.append(separatorSpaceWithBackground);
            } else {
                line.append(separatorSpace);
            }
            for (int col = 0; col < width; col++) {
                if (row + 1 < board.getHeight() && board.isOnField(row + 1, col)) {
                    line.append(separatorSlotWithBackground);
                } else {
                    if (board.isOnField(row + 1, col + 1)) {
                        line.append(reset + separatorSlotWithTailBackground);
                    } else {
                        line.append(reset + separatorSlot);
                    }
                }
            }
        } else {

            if (board.isOnField(row, 0) || (row + 1 < board.getHeight() && board.isOnField(row + 1, 0))) {
                line.append(separatorSpaceWithBackground);
            } else {
                line.append(separatorSpace);
            }
            for (int col = 0; col < width; col++) {
                if (board.isOnField(row, col) || (row + 1 < board.getHeight() && board.isOnField(row + 1, col))) {
                    line.append(separatorSlotWithBackground);
                } else {
                    if (col + 1 < width && (board.isOnField(row, col + 1) || (row < board.getHeight() - 1 && board.isOnField(row + 1, col + 1)))) {
                        line.append(reset + separatorSlotWithTailBackground);
                    } else {
                        line.append(reset + separatorSlot);
                    }
                }
            }
        }
        line.append(reset);
        line.append(newLine);
        return line;
    }

    private String getColoredCircle(Disc disc) {
        return switch (disc) {
            case RED -> red + circle + reset + backgroundColor + borderColor;
            case BLUE -> blue + circle + reset + backgroundColor + borderColor;
            case YELLOW -> yellow + circle + reset + backgroundColor + borderColor;
            case GREEN -> green + circle + reset + backgroundColor + borderColor;
        };
    }

    private StringBuilder getFooter(int width) {
        StringBuilder line = new StringBuilder();
        line.append(footerSpace);
        for (int col = 1; col <= width; col++) {
            line.append(String.format(footerSlotTemplate, col));
        }
        line.append(newLine);
        return line;
    }
}
