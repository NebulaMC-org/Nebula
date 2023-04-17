/*
 * Iris is a World Generator for Minecraft Bukkit Servers
 * Copyright (c) 2022 Arcane Arts (Volmit Software)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.nebulamc.plugin.utils.noise;

public class WhiteNoise implements NoiseGenerator {
    private final FastNoise n;

    public WhiteNoise(long seed) {
        n = new FastNoise(new RNG(seed).imax());
    }

    public boolean isStatic() {
        return true;
    }

    public boolean isNoScale() {
        return true;
    }

    private double f(double m) {
        return (m % 8192) * 1024;
    }

    @Override
    public double noise(double x) {
        return (n.GetWhiteNoise(f(x), 0d) / 2D) + 0.5D;
    }

    @Override
    public double noise(double x, double z) {
        return (n.GetWhiteNoise(f(x), f(z)) / 2D) + 0.5D;
    }

    @Override
    public double noise(double x, double y, double z) {
        return (n.GetWhiteNoise(f(x), f(y), f(z)) / 2D) + 0.5D;
    }
}
