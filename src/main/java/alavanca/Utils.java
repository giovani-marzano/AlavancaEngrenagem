package alavanca;

import java.awt.geom.Point2D;

/**
 * Coleção de métodos utilitários
 */
public final class Utils {
	private Utils() {
	}

	/**
	 * Calcula a posição angular na circunferencia 1 em que ela intercepta a
	 * circunferência 2.
	 * 
	 * @param cen1
	 *            Centro da circunferência 1
	 * @param r1
	 *            Raio da circunferência 1
	 * @param cen2
	 *            Centro da circunferência 2
	 * @param r2
	 *            Raio da circunferência 2
	 * @return Angulo na circunferência 1 do ponto de interceção.
	 */
	public static double circlesIntersectionAlfa(final Point2D cen1, final double r1, final Point2D cen2,
			final double r2) {
		return circlesIntersectionAlfa(cen1, r1, cen2, r2, cen1.distance(cen2));
	}

	/**
	 * Calcula a posição angular na circunferencia 1 em que ela intercepta a
	 * circunferência 2.
	 * 
	 * @param cen1
	 *            Centro da circunferência 1
	 * @param r1
	 *            Raio da circunferência 1
	 * @param cen2
	 *            Centro da circunferência 2
	 * @param r2
	 *            Raio da circunferência 2
	 * @param dist
	 *            Distância entre os centros das circunferências.
	 * @return Angulo na circunferência 1 do ponto de interceção.
	 */
	public static double circlesIntersectionAlfa(final Point2D cen1, final double r1, final Point2D cen2,
			final double r2, final double dist) {
		// X e Y de um ponto de interseção em um sistema de coordenadas em que
		// a origem é o centro do círculo 1 e o eixo x é a linha que liga o
		// centro do círculo 1 ao centro do círculo 2
		double px = (r1 * r1 - r2 * r2 + dist * dist) / (2 * dist);
		double py = -Math.sqrt(r1 * r1 - px * px);

		// Convertendo px e py para as coordenadas reais
		//
		// Coordenadas do vetor unitario que liga cen1 e cen2
		double vx = (cen2.getX() - cen1.getX()) / dist;
		double vy = (cen2.getY() - cen1.getY()) / dist;

		// Para encontrar o ponto real, devemos andar:
		// - px na direção (vx,vy)
		// - py na direção (-vy, vx), que é perpendicular a (vx,vy)
		double x = px * vx + py * (-vy);
		double y = px * vy + py * vx;

		return Math.atan2(-y, x);
	}

	/**
	 * Calcula o raio de um circulo para que este cruze com o círculo fornecido no
	 * ponto indicado.
	 * 
	 * @param cen1
	 *            Centro do círculo fornecido.
	 * @param r1
	 *            Raio do círculo fornecido.
	 * @param alfa
	 *            Posição angular do ponto de interseção contada a partir do eixo
	 *            que liga os centros dos dois círculos.
	 * @param cen2
	 *            Centro do círculo calculado.
	 * @return Raio do círculo para que ocorra a interseção desejada
	 */
	public static double circlesIntersectionRadius(final Point2D cen1, final double r1, final double alfa,
			final Point2D cen2) {
		return circlesIntersectionRadius(cen1, r1, alfa, cen1.distance(cen2));
	}

	/**
	 * Calcula o raio de um circulo para que este cruze com o círculo fornecido no
	 * ponto indicado.
	 * 
	 * @param cen1
	 *            Centro do círculo fornecido.
	 * @param r1
	 *            Raio do círculo fornecido.
	 * @param alfa
	 *            Posição angular do ponto de interseção contada a partir do eixo
	 *            que liga os centros dos dois círculos.
	 * @param dist
	 *            Distância do centro do círculo calculado ao centro do círculo
	 *            fornecido.
	 * @return Raio do círculo para que ocorra a interseção desejada
	 */
	public static double circlesIntersectionRadius(final Point2D cen1, final double r1, final double alfa,
			final double dist) {
		double px = r1 * Math.cos(alfa);
		double py = r1 * Math.sin(alfa);

		return Math.sqrt((dist - px) * (dist - px) + py * py);
	}
}
