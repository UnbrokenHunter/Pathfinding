package Utilities;

import java.awt.Color;
import java.util.Hashtable;

public class ColorInterpolator {
        private final Color[] colors;
        private final int numColors;

        // Constructor for two colors
        public ColorInterpolator(Color startColor, Color endColor, int numColors) {
                if (startColor == null || endColor == null) {
                        throw new IllegalArgumentException("Colors cannot be null.");
                }

                this.colors = new Color[] { startColor, endColor };
                this.numColors = numColors;
        }

        // Constructor for an array of colors
        public ColorInterpolator(Color[] colors, int numColors) {
                if (colors == null || colors.length < 2) {
                        throw new IllegalArgumentException("There must be at least two colors to interpolate.");
                }

                this.colors = colors;
                this.numColors = numColors;
        }

        public Color getColor(int step) {
                if (step >= numColors) {
                        return colors[colors.length - 1];
                }

                float totalColorIntervals = colors.length - 1;
                float stepsPerColorInterval = numColors / totalColorIntervals;

                int colorIndex = (int) (step / stepsPerColorInterval);
                float ratioWithinCurrentInterval = (step % stepsPerColorInterval) / stepsPerColorInterval;

                return interpolateColor(colors[colorIndex], colors[colorIndex + 1], ratioWithinCurrentInterval);
        }

        private Color interpolateColor(Color c1, Color c2, float ratio) {
                int red = interpolate(c1.getRed(), c2.getRed(), ratio);
                int green = interpolate(c1.getGreen(), c2.getGreen(), ratio);
                int blue = interpolate(c1.getBlue(), c2.getBlue(), ratio);

                return new Color(red, green, blue);
        }

        private int interpolate(int start, int end, float ratio) {
                return (int) (start + ratio * (end - start));
        }

        // PRESETS
        // Different color palettes

        // The "Standard" palette fading from bright yellow to dark grey
        private static final Color[] STANDARD = {
                        Color.YELLOW, // Bright yellow
                        Color.DARK_GRAY // Dark grey
        };

        // A palette of oceanic blues
        private static final Color[] OCEANIC_BLUES = {
                        new Color(240, 248, 255), // Alice blue
                        new Color(0, 191, 255), // Deep sky blue
                        new Color(25, 25, 112) // Midnight blue
        };

        // A palette of soft pastels
        private static final Color[] SOFT_PASTELS = {
                        new Color(255, 182, 193), // Light pink
                        new Color(173, 216, 230), // Light blue
                        new Color(255, 239, 213) // Papaya whip
        };

        // A palette of cool blue shades
        private static final Color[] COOL_BLUES = {
                        new Color(173, 216, 230), // Light blue
                        new Color(100, 149, 237), // Cornflower blue
                        new Color(70, 130, 180) // Steel blue
        };

        // A palette of warm sunset shades
        private static final Color[] WARM_SUNSET = {
                        Color.YELLOW, // Bright yellow
                        new Color(255, 99, 71), // Tomato red
                        new Color(148, 0, 211) // Dark violet
        };

        // A palette of earthy greens
        private static final Color[] EARTHY_GREENS = {
                        new Color(144, 238, 144), // Light green
                        new Color(107, 142, 35), // Olive drab
                        new Color(85, 107, 47) // Dark olive green
        };

        // A palette of fiery reds
        private static final Color[] FIERY_REDS = {
                        Color.RED, // Bright red
                        new Color(255, 69, 0), // Bright orange-red
                        new Color(139, 0, 0) // Dark red
        };

        // A palette of elegant purples
        private static final Color[] ELEGANT_PURPLES = {
                        new Color(147, 112, 219), // Medium purple
                        new Color(138, 43, 226), // Blue-violet
                        new Color(75, 0, 130) // Indigo
        };

        // A palette of vibrant teals
        private static final Color[] VIBRANT_TEALS = {
                        new Color(0, 128, 128), // Teal
                        new Color(64, 224, 208), // Turquoise
                        new Color(127, 255, 212) // Aquamarine
        };

        // A palette of rich browns
        private static final Color[] RICH_BROWNS = {
                        new Color(165, 42, 42), // Brown
                        new Color(210, 105, 30), // Chocolate
                        new Color(139, 69, 19) // Saddle brown
        };

        // A palette of calming lavenders
        private static final Color[] CALMING_LAVENDERS = {
                        new Color(230, 230, 250), // Lavender
                        new Color(221, 160, 221), // Plum
                        new Color(153, 50, 204) // Dark orchid
        };

        // A palette of golden hues
        private static final Color[] GOLDEN_HUES = {
                        new Color(255, 215, 0), // Gold
                        new Color(238, 232, 170), // Pale goldenrod
                        new Color(189, 183, 107) // Dark khaki
        };

        // A palette of pinks
        private static final Color[] CHERRY_BLOSSOM = {
                        new Color(239, 146, 239), // Pink
                        new Color(255, 217, 253), // Light Pink
                        new Color(232, 45, 223), // Dark Pink
                        new Color(239, 146, 239), // Pink
                        new Color(255, 217, 253), // Light Pink
                        new Color(246, 107, 240), // Mid Pink
        };

        // A palette of pinks
        private static final Color[] CHRISTMAS = {
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
                        new Color(194, 7, 0), // Light Red
                        new Color(184, 47, 47), // Red
                        new Color(20, 56, 32), // Dark Green
                        new Color(255, 255, 255), // White
                        new Color(11, 117, 48), // Green
                        new Color(23, 194, 83), // Light Green
                        new Color(255, 255, 255), // White
        };

        // A palette representing the colors of the rainbow
        private static final Color[] RAINBOW = {
                        Color.RED,
                        Color.ORANGE,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.BLUE,
                        new Color(75, 0, 130), // Indigo
                        new Color(143, 0, 255) // Violet
        };

        private static final Hashtable<String, Color[]> PALETTES;

        static {
                PALETTES = new Hashtable<>();
                PALETTES.put("Standard", STANDARD);
                PALETTES.put("Cool Blues", COOL_BLUES);
                PALETTES.put("Warm Sunset", WARM_SUNSET);
                PALETTES.put("Earthy Greens", EARTHY_GREENS);
                PALETTES.put("Fiery Reds", FIERY_REDS);
                PALETTES.put("Elegant Purple", ELEGANT_PURPLES);
                PALETTES.put("Oceanic Blues", OCEANIC_BLUES);
                PALETTES.put("Soft Pastels", SOFT_PASTELS);
                PALETTES.put("Vibrant Teals", VIBRANT_TEALS);
                PALETTES.put("Rich Browns", RICH_BROWNS);
                PALETTES.put("Calming Lavenders", CALMING_LAVENDERS);
                PALETTES.put("Golden Hues", GOLDEN_HUES);
                PALETTES.put("Cherry Blossom", CHERRY_BLOSSOM);
                PALETTES.put("Christmas", CHRISTMAS);
                PALETTES.put("Rainbow", RAINBOW);
        }

        public static Hashtable<String, Color[]> getPalettes() {
                return PALETTES;
        }

        public static Color[] getPalette(String name) {
                return PALETTES.get(name);
        }
}
