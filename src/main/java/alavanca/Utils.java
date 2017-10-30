package alavanca;

import java.awt.geom.Point2D;

/**
 * Coleção de métodos utilitários
 */
public final class Utils {
	private Utils() {}
	
	/**
	 * Calcula a posição angular na circunferencia 1 em que ela intercepta a
	 * circunferência 2.
	 * 
	 * @param cen1 Centro da circunferência 1
	 * @param cen2 Centro da circunferência 2
	 * @param r1 Raio da circunferência 1
	 * @param r2 Raio da circunferência 2
	 * @return Angulo na circunferência 1 do ponto de interceção.
	 */
	public static double circlesIntersectionAlfa(final Point2D cen1, final Point2D cen2,
			final double r1, final double r2) {
		return circlesIntersectionAlfa(cen1, cen2, r1, r2, cen1.distance(cen2));
	}

	/**
	 * Calcula a posição angular na circunferencia 1 em que ela intercepta a
	 * circunferência 2.
	 * 
	 * @param cen1 Centro da circunferência 1
	 * @param cen2 Centro da circunferência 2
	 * @param r1 Raio da circunferência 1
	 * @param r2 Raio da circunferência 2
	 * @param dist Distância entre os centros das circunferências.
	 * @return Angulo na circunferência 1 do ponto de interceção.
	 */
	public static double circlesIntersectionAlfa(final Point2D cen1, final Point2D cen2,
			final double r1, final double r2, final double dist) {
		// X e Y de um ponto de interseção em um sistema de coordenadas em que
		// a origem é o centro da engrenagem e o eixo x é a linha que liga o
		// centro da engrenagem maior e o ponto de conexão na engrenagem menor
		double px = (r1*r1 - r2*r2 + dist*dist)/(2*dist);
		double py = -Math.sqrt(r1*r1 - px*px);
		
		// Convertendo px e py para as coordenadas reais
		//
		// Coordenadas do vetor unitario que liga cenEng e cenBar
		double vx = (cen2.getX() - cen1.getX())/dist;
		double vy = (cen2.getY() - cen1.getY())/dist;
		
		// Para encontrar o ponto real, devemos andar:
		// - px na direção (vx,vy)
		// - py na direção (-vy, vx), que é perpendicular a (vx,vy)
		double x = px * vx + py * (-vy);
		double y = px * vy + py * vx;

		double alfa = Math.atan2(-y, x);
		return alfa;
	}
}
