import { defineConfig } from "allure";

export default defineConfig({
	categories: {
		rules: [
			{
				name: "Дефект API",
				matchers: {
					statuses: ["failed"],
					message: /Expected status code|JSON schema|matchesJsonSchema/,
				},
			},
			{
				name: "Ошибка автотеста",
				matchers: {
					statuses: ["broken"],
					message: /NullPointerException|IllegalStateException/,
				},
			},
			{
				name: "Инфраструктура",
				matchers: {
					statuses: ["broken"],
					message: /SocketTimeoutException|ConnectException|UnknownHostException/,
				},
			},
		],
	},
});
