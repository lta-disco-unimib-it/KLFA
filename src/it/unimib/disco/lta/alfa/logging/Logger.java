/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.logging;

import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;




public class Logger {
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("klfa");

	public static void addHandler(Handler handler) throws SecurityException {
		logger.addHandler(handler);
	}

	public static void config(String msg) {
		logger.config(msg);
	}

	public static void entering(String sourceClass, String sourceMethod, Object param1) {
		logger.entering(sourceClass, sourceMethod, param1);
	}

	public static void entering(String sourceClass, String sourceMethod,
			Object[] params) {
		logger.entering(sourceClass, sourceMethod, params);
	}

	public static void entering(String sourceClass, String sourceMethod) {
		logger.entering(sourceClass, sourceMethod);
	}

	

	public static void exiting(String sourceClass, String sourceMethod, Object result) {
		logger.exiting(sourceClass, sourceMethod, result);
	}

	public static void exiting(String sourceClass, String sourceMethod) {
		logger.exiting(sourceClass, sourceMethod);
	}

	public static void fine(String msg) {
		System.out.println(msg);
	}

	public static void finer(String msg) {
		System.out.println(msg);
	}

	public static void finest(String msg) {
		System.out.println(msg);
	}

	public static Filter getFilter() {
		return logger.getFilter();
	}

	public static Handler[] getHandlers() {
		return logger.getHandlers();
	}

	public static Level getLevel() {
		return logger.getLevel();
	}

	public static String getName() {
		return logger.getName();
	}

	public static java.util.logging.Logger getParent() {
		return logger.getParent();
	}

	public static ResourceBundle getResourceBundle() {
		return logger.getResourceBundle();
	}

	public static String getResourceBundleName() {
		return logger.getResourceBundleName();
	}

	public static boolean getUseParentHandlers() {
		return logger.getUseParentHandlers();
	}

	
	public static void info(String msg) {
		System.out.println(msg);
	}

	public static boolean isLoggable(Level level) {
		return true;
	}

	public static void log(Level level, String msg, Object param1) {
		logger.log(level, msg, param1);
	}

	public static void log(Level level, String msg, Object[] params) {
		logger.log(level, msg, params);
	}

	public static void log(Level level, String msg, Throwable thrown) {
		logger.log(level, msg, thrown);
	}

	public static void log(Level level, String msg) {
		logger.log(level, msg);
	}

	public static void log(LogRecord record) {
		logger.log(record);
	}

	public static void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object param1) {
		logger.logp(level, sourceClass, sourceMethod, msg, param1);
	}

	public static void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object[] params) {
		logger.logp(level, sourceClass, sourceMethod, msg, params);
	}

	public static void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Throwable thrown) {
		logger.logp(level, sourceClass, sourceMethod, msg, thrown);
	}

	public static void logp(Level level, String sourceClass, String sourceMethod,
			String msg) {
		logger.logp(level, sourceClass, sourceMethod, msg);
	}

	public static void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object param1) {
		logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, param1);
	}

	public static void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object[] params) {
		logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, params);
	}

	public static void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Throwable thrown) {
		logger.logrb(level, sourceClass, sourceMethod, bundleName, msg, thrown);
	}

	public static void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg) {
		logger.logrb(level, sourceClass, sourceMethod, bundleName, msg);
	}

	public static void removeHandler(Handler handler) throws SecurityException {
		logger.removeHandler(handler);
	}

	public static void setFilter(Filter newFilter) throws SecurityException {
		logger.setFilter(newFilter);
	}

	public static void setLevel(Level newLevel) throws SecurityException {
		logger.setLevel(newLevel);
	}

	public static void setParent(java.util.logging.Logger parent) {
		logger.setParent(parent);
	}

	public static void setUseParentHandlers(boolean useParentHandlers) {
		logger.setUseParentHandlers(useParentHandlers);
	}

	public static void severe(String msg) {
		logger.severe(msg);
	}

	public static void throwing(String sourceClass, String sourceMethod,
			Throwable thrown) {
		logger.throwing(sourceClass, sourceMethod, thrown);
	}

	public static void warning(String msg) {
		logger.warning(msg);
	}

	
}
